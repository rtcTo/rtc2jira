package to.rtc.rtc2jira.extract;

import static to.rtc.rtc2jira.storage.WorkItemConstants.ID;

import java.io.IOException;
import java.util.List;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.ibm.team.repository.client.ILoginHandler2;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import com.ibm.team.repository.client.login.UsernameAndPasswordLoginInfo;
import com.ibm.team.repository.common.PermissionDeniedException;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

/**
 * Extracts WorkItems from RTC
 * 
 * @author roman.schaller
 *
 */
public class RTCExtractor {

  private Settings settings;
  private StorageEngine storageEngine;

  public RTCExtractor(Settings settings, StorageEngine storageEngine) {
    this.settings = settings;
    this.storageEngine = storageEngine;
  }

  public static boolean isLoginPossible(Settings settings) {
    boolean isLoginPossible = false;
    if (settings.hasRtcProperties()) {
      TeamPlatform.startup();
      try {
        login(settings).logout();
        isLoginPossible = true;
      } catch (TeamRepositoryException e) {
        System.out.println("Unable to login into RTC Repository");
        e.printStackTrace();
      } finally {
        TeamPlatform.shutdown();
      }
    }
    return isLoginPossible;
  }

  public void extract() {
    TeamPlatform.startup();
    try {
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
    repo.registerLoginHandler((ILoginHandler2) loginHandler -> new UsernameAndPasswordLoginInfo(
        userId, password));
    repo.login(null);
    return repo;
  }

  private void processWorkItems(ITeamRepository repo, Iterable<Integer> workItemRange)
      throws TeamRepositoryException, IOException {
    IWorkItemClient workItemClient = (IWorkItemClient) repo.getClientLibrary(IWorkItemClient.class);
    AttachmentHandler attachmentHandler =
        new AttachmentHandler(repo, storageEngine.getAttachmentStorage());
    for (Integer currentWorkItemId : workItemRange) {
      processWorkItem(repo, workItemClient, currentWorkItemId, attachmentHandler);
    }
  }

  private void processWorkItem(ITeamRepository repo, IWorkItemClient workItemClient,
      int workItemId, AttachmentHandler attachmentHandler) throws TeamRepositoryException,
      IOException {
    try {
      System.out.println("WorkItem " + workItemId + ":");
      System.out.println("****************************");
      IWorkItem workItem =
          workItemClient.findWorkItemById(workItemId, IWorkItem.FULL_PROFILE, null);
      if (workItem == null) {
        System.out.println("Not present. Will skip this one.");
        System.out.println();
        return;
      }

      storageEngine.withDB(db -> {
        OSQLSynchQuery<ODocument> query =
            new OSQLSynchQuery<ODocument>("select * from WorkItem where ID = :ID");
        List<ODocument> result = db.query(query, workItem.getId());
        final ODocument doc;
        if (result.size() > 0) {
          doc = result.get(0);
        } else {
          doc = new ODocument("WorkItem");
          doc.field(ID, workItem.getId());
        }
        saveAttributes(workItemClient, workItem, doc);
        attachmentHandler.saveAttachements(workItem);
        updateWorkItem(doc, repo, workItem);
        doc.save();
      });
      System.out.println();
      System.out.println();
    } catch (PermissionDeniedException e) {
      System.out.println("***** Too bad: I must not access WorkItem " + workItemId
          + ". They say it is forbidden... :-(");
    } catch (RuntimeException e) {
      System.out.println("***** Problem: " + e.getMessage());
      e.printStackTrace();
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

  private void updateWorkItem(ODocument doc, ITeamRepository repo, IWorkItem workItem) {
    doc.field("createDate", workItem.getCreationDate());
  }
}
