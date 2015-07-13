package rtc.to.rtc2jira.extract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

import rtc.to.rtc2jira.Settings;
import rtc.to.rtc2jira.storage.StorageEngine;

import com.ibm.team.links.common.IReference;
import com.ibm.team.process.client.IProcessClientService;
import com.ibm.team.process.common.IProjectArea;
import com.ibm.team.repository.client.ILoginHandler2;
import com.ibm.team.repository.client.ILoginInfo2;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import com.ibm.team.repository.client.login.UsernameAndPasswordLoginInfo;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IAuditableClient;
import com.ibm.team.workitem.client.IWorkItemClient;
import com.ibm.team.workitem.common.IWorkItemCommon;
import com.ibm.team.workitem.common.model.IAttachment;
import com.ibm.team.workitem.common.model.IAttachmentHandle;
import com.ibm.team.workitem.common.model.IAttribute;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.ibm.team.workitem.common.model.IWorkItemReferences;
import com.ibm.team.workitem.common.model.WorkItemEndPoints;

public class RTCExtractor {

  private Settings settings;
  private StorageEngine storageEngine;

  public RTCExtractor(Settings settings, StorageEngine storageEngine) {
    this.settings = settings;
    this.storageEngine = storageEngine;
  }

  public void extract() {
    try (Scanner sc = new Scanner(System.in)) {
      final String userId = settings.getRtcUser();
      final String password = settings.getRtcPassword();
      String repoUri = settings.getRtcUrl();
      TeamPlatform.startup();
      try {
        final ITeamRepository repo = TeamPlatform.getTeamRepositoryService().getTeamRepository(repoUri);
        repo.registerLoginHandler(new ILoginHandler2() {
          @Override
          public ILoginInfo2 challenge(ITeamRepository repo) {
            return new UsernameAndPasswordLoginInfo(userId, password);
          }
        });
        repo.login(null);
        getWorkItems(repo);
        repo.logout();
      } catch (TeamRepositoryException | IOException e) {
        e.printStackTrace();
      } finally {
        TeamPlatform.shutdown();
      }
    }
  }

  private void getWorkItems(ITeamRepository repo) throws TeamRepositoryException, IOException {
    IProcessClientService processClientService =
        (IProcessClientService) repo.getClientLibrary(IProcessClientService.class);
    URI projectareaUri = URI.create(settings.getRtcProjectarea().replaceAll(" ", "%20"));
    IProjectArea projectArea = (IProjectArea) processClientService.findProcessArea(projectareaUri, null, null);

    IWorkItemClient workItemClient = (IWorkItemClient) repo.getClientLibrary(IWorkItemClient.class);
    IWorkItem workItem = workItemClient.findWorkItemById(33481, IWorkItem.FULL_PROFILE, null);
    List<IAttribute> allAttributes = workItemClient.findAttributes(projectArea, null);
    for (IAttribute attribute : allAttributes) {
      if (workItem.hasAttribute(attribute)) {
        Object value = workItem.getValue(attribute);
        String formattedOutput =
            String.format("Identifier: %s \t Display Name: %s \t Type: %s \t Value: %s", attribute.getIdentifier(),
                attribute.getDisplayName(), attribute.getAttributeType(), value);
        System.out.println(formattedOutput);

      }
    }
    saveAttachements(repo, workItem);
  }

  private void saveAttachements(ITeamRepository repo, IWorkItem workItem) throws TeamRepositoryException, IOException {
    IWorkItemCommon common = (IWorkItemCommon) repo.getClientLibrary(IWorkItemCommon.class);
    IWorkItemReferences workitemReferences = common.resolveWorkItemReferences(workItem, null);
    List<IReference> references = workitemReferences.getReferences(WorkItemEndPoints.ATTACHMENT);
    for (IReference iReference : references) {
      IAttachmentHandle attachHandle = (IAttachmentHandle) iReference.resolve();
      IAuditableClient auditableClient = (IAuditableClient) repo.getClientLibrary(IAuditableClient.class);
      IAttachment attachment = auditableClient.resolveAuditable(attachHandle, IAttachment.DEFAULT_PROFILE, null);
      saveAttachment(repo, attachment);
    }
  }

  private void saveAttachment(ITeamRepository teamRepository, IAttachment attachment) throws TeamRepositoryException,
      IOException {
    File save = new File(attachment.getName());
    try (OutputStream out = new FileOutputStream(save)) {
      teamRepository.contentManager().retrieveContent(attachment.getContent(), out, null);
    }
  }
}
