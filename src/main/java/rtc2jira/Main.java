package rtc2jira;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.ibm.team.links.common.IReference;
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

/**
 * @author roman.schaller
 *
 */
public class Main {

  public static void main(String[] args) {
    Options options = new Options();
    Option rtcUrlOpt = new Option("r", true, "RTC URL like 'https://rtc.local/ccm/'");
    rtcUrlOpt.setRequired(true);
    options.addOption(rtcUrlOpt);
    Option userOpt = new Option("u", true, "User");
    rtcUrlOpt.setRequired(true);
    options.addOption(userOpt);
    Option pwOpt = new Option("p", true, "Password");
    pwOpt.setRequired(true);
    options.addOption(pwOpt);
    Option bbUrlOpt = new Option("b", true, "BitBucket URL");
    options.addOption(bbUrlOpt);

    CommandLine cmd;
    try {
      CommandLineParser parser = new DefaultParser();
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("rtc2jira ", options);
      System.exit(1);
      return;
    }

    IProgressMonitor myProgressMonitor = new MyProgressMonitor();
    try (Scanner sc = new Scanner(System.in)) {
      final String userId = cmd.getOptionValue(userOpt.getOpt());
      final String password = cmd.getOptionValue(pwOpt.getOpt());
      String repoUri = cmd.getOptionValue(rtcUrlOpt.getOpt());
      TeamPlatform.startup();
      try {
        final ITeamRepository repo = TeamPlatform.getTeamRepositoryService().getTeamRepository(repoUri);
        repo.registerLoginHandler(new ILoginHandler2() {
          @Override
          public ILoginInfo2 challenge(ITeamRepository repo) {
            return new UsernameAndPasswordLoginInfo(userId, password);
          }
        });
        repo.login(myProgressMonitor);
        getWorkItems(repo);
        repo.logout();
      } catch (TeamRepositoryException | IOException e) {
        e.printStackTrace();
      } finally {
        TeamPlatform.shutdown();
      }
    }
  }

  private static void getWorkItems(ITeamRepository repo) throws TeamRepositoryException, IOException {
    IWorkItemClient workItemClient = (IWorkItemClient) repo.getClientLibrary(IWorkItemClient.class);
    IWorkItem workItem = workItemClient.findWorkItemById(33481, IWorkItem.FULL_PROFILE, null);
    List<IAttribute> allAttributes = workItemClient.findAttributes(workItem.getProjectArea(), null);
    for (IAttribute attribute : allAttributes) {
      if (workItem.hasAttribute(attribute)) {
        System.out.println(workItem.getValue(attribute));
      }
    }
    saveAttachements(repo, workItem);
  }

  private static void saveAttachements(ITeamRepository repo, IWorkItem workItem) throws TeamRepositoryException,
      IOException {
    IWorkItemCommon common = (IWorkItemCommon) repo.getClientLibrary(IWorkItemCommon.class);
    IWorkItemReferences workitemReferences = common.resolveWorkItemReferences(workItem, null);
    List<IReference> references = workitemReferences.getReferences(WorkItemEndPoints.ATTACHMENT);
    for (IReference iReference : references) {
      IAttachmentHandle attachHandle = (IAttachmentHandle) iReference.resolve();
      IAuditableClient auditableClient = (IAuditableClient) repo.getClientLibrary(IAuditableClient.class);
      IAttachment attachment =
          (IAttachment) auditableClient.resolveAuditable((IAttachmentHandle) attachHandle, IAttachment.DEFAULT_PROFILE,
              null);
      saveAttachment(repo, attachment);
    }
  }

  private static void saveAttachment(ITeamRepository teamRepository, IAttachment attachment)
      throws TeamRepositoryException, IOException {
    File save = new File(attachment.getName());
    try (OutputStream out = new FileOutputStream(save)) {
      teamRepository.contentManager().retrieveContent(attachment.getContent(), out, null);
    }
  }
}
