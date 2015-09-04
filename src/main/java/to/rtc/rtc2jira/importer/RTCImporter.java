package to.rtc.rtc2jira.importer;

import static to.rtc.rtc2jira.storage.FieldNames.ID;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.storage.Attachment;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.ibm.team.repository.client.ILoginHandler2;
import com.ibm.team.repository.client.ILoginInfo2;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import com.ibm.team.repository.client.login.UsernameAndPasswordLoginInfo;
import com.ibm.team.repository.common.PermissionDeniedException;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.common.internal.util.SeparatedStringList;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

/**
 * Imports WorkItems from RTC
 * 
 * @author roman.schaller
 *
 */
public class RTCImporter {
  private static final Logger LOGGER = Logger.getLogger(RTCImporter.class.getName());

  private Settings settings;
  private StorageEngine storageEngine;
  private List<Integer> permissionDeniedWorkitems;
  private List<Integer> notPresentWorkitems;

  public RTCImporter(Settings settings, StorageEngine storageEngine) {
    this.settings = settings;
    this.storageEngine = storageEngine;
    this.permissionDeniedWorkitems = new LinkedList<>();
    this.notPresentWorkitems = new LinkedList<>();
  }

  public static boolean isLoginPossible(Settings settings) {
    boolean isLoginPossible = false;
    if (settings.hasRtcProperties()) {
      TeamPlatform.startup();
      try {
        login(settings).logout();
        isLoginPossible = true;
      } catch (TeamRepositoryException e) {
        LOGGER.log(Level.SEVERE, "Unable to login into RTC Repository", e);
      } finally {
        TeamPlatform.shutdown();
      }
    }
    return isLoginPossible;
  }

  public void doImport() {
    TeamPlatform.startup();
    try {
      LOGGER.info("***********************");
      LOGGER.info("Starting to import work items.");
      ITeamRepository repo = login(settings);
      processWorkItems(repo, settings.getRtcWorkItemRange());
      repo.logout();
    } catch (TeamRepositoryException | IOException e) {
      e.printStackTrace();
    } finally {
      TeamPlatform.shutdown();
    }
  }

  private static ITeamRepository login(Settings settings) throws TeamRepositoryException {
    final String userId = settings.getRtcUser();
    final String password = settings.getRtcPassword();
    String repoUri = settings.getRtcUrl();
    final ITeamRepository repo = TeamPlatform.getTeamRepositoryService().getTeamRepository(repoUri);
    if (settings.hasProxySettings()) {
      repo.setProxy(settings.getProxyHost(), Integer.parseInt(settings.getProxyPort()), null, null);
    }
    repo.registerLoginHandler(new ILoginHandler2() {
      @Override
      public ILoginInfo2 challenge(ITeamRepository repo) {
        return new UsernameAndPasswordLoginInfo(userId, password);
      }
    });
    repo.registerLoginHandler((ILoginHandler2) loginHandler -> new UsernameAndPasswordLoginInfo(userId, password));
    repo.login(null);
    return repo;
  }

  private void processWorkItems(ITeamRepository repo, Iterable<Integer> workItemRange) throws TeamRepositoryException,
      IOException {
    IWorkItemClient workItemClient = (IWorkItemClient) repo.getClientLibrary(IWorkItemClient.class);
    AttachmentHandler attachmentHandler = new AttachmentHandler(repo, storageEngine.getAttachmentStorage());
    int counter = 0;
    for (Integer currentWorkItemId : workItemRange) {
      processWorkItem(repo, workItemClient, currentWorkItemId, attachmentHandler);
      LOGGER.info(String.format("processed %s items...", ++counter));
    }
    LOGGER.info(String.format("There were %s items which I had no permission to access.",
        permissionDeniedWorkitems.size()));
    for (Integer id : permissionDeniedWorkitems) {
      LOGGER.info(String.format("%d, ", id));
    }
    LOGGER.info(String.format("There were %s items which were not present.", notPresentWorkitems.size()));
    for (Integer id : notPresentWorkitems) {
      LOGGER.info(String.format("%d, ", id));
    }
  }

  private void processWorkItem(ITeamRepository repo, IWorkItemClient workItemClient, int workItemId,
      AttachmentHandler attachmentHandler) throws TeamRepositoryException, IOException {
    try {
      IWorkItem workItem;
      try {
        workItem = workItemClient.findWorkItemById(workItemId, IWorkItem.FULL_PROFILE, null);
      } catch (PermissionDeniedException e) {
        this.permissionDeniedWorkitems.add(workItemId);
        return;
      }
      if (workItem == null) {
        this.notPresentWorkitems.add(workItemId);
        return;
      }

      storageEngine.withDB(db -> {
        OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<ODocument>("select * from WorkItem where ID = :ID");
        List<ODocument> result = db.query(query, workItem.getId());
        final ODocument doc;
        if (result.size() > 0) {
          doc = result.get(0);
        } else {
          doc = new ODocument("WorkItem");
          doc.field(ID, workItem.getId());
          doc.field(Attachment.EXPORTED_ATTACHMENTS_PROPERTY, new SeparatedStringList());
        }
        saveAttributes(workItemClient, workItem, doc);
        attachmentHandler.saveAttachements(workItem);

        doc.save();
      });
    } catch (RuntimeException e) {
      LOGGER.log(Level.SEVERE, "***** Problem processing workitem " + workItemId, e);
    }
  }

  private void saveAttributes(IWorkItemClient workItemClient, IWorkItem workItem, ODocument doc) {
    List<IAttribute> allAttributes;
    try {
      allAttributes = workItemClient.findAttributes(workItem.getProjectArea(), null);
      new AttributeMapper().map(allAttributes, doc, workItem);
    } catch (TeamRepositoryException e) {
      throw new RuntimeException("Cannot get attributes from project area.", e);
    }
  }
}
