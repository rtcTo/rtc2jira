/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.extract;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import to.rtc.rtc2jira.storage.Attachment;
import to.rtc.rtc2jira.storage.AttachmentStorage;

import com.ibm.team.links.common.IReference;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.workitem.client.IAuditableClient;
import com.ibm.team.workitem.common.IWorkItemCommon;
import com.ibm.team.workitem.common.model.IAttachment;
import com.ibm.team.workitem.common.model.IAttachmentHandle;
import com.ibm.team.workitem.common.model.IWorkItem;
import com.ibm.team.workitem.common.model.IWorkItemReferences;
import com.ibm.team.workitem.common.model.WorkItemEndPoints;

/**
 * @author roman.schaller
 *
 */
public class AttachmentHandler {

  private ITeamRepository repo;
  private AttachmentStorage attachmentStorage;

  public AttachmentHandler(ITeamRepository repo, AttachmentStorage attachmentStorage) {
    this.repo = repo;
    this.attachmentStorage = attachmentStorage;
  }

  public void saveAttachements(IWorkItem workItem) {
    IWorkItemCommon common = (IWorkItemCommon) repo.getClientLibrary(IWorkItemCommon.class);
    IWorkItemReferences workitemReferences;
    try {
      workitemReferences = common.resolveWorkItemReferences(workItem, null);
      List<IReference> references = workitemReferences.getReferences(WorkItemEndPoints.ATTACHMENT);
      for (IReference iReference : references) {
        IAttachmentHandle attachHandle = (IAttachmentHandle) iReference.resolve();
        IAuditableClient auditableClient =
            (IAuditableClient) repo.getClientLibrary(IAuditableClient.class);
        IAttachment attachment =
            auditableClient.resolveAuditable(attachHandle, IAttachment.DEFAULT_PROFILE, null);
        saveAttachment(workItem.getId(), attachment);
      }
    } catch (TeamRepositoryException | IOException e) {
      System.out.println("Cannot download attachement for WorkItem " + workItem.getId() + "("
          + e.getMessage() + ")");
    }
  }

  private void saveAttachment(long workitemId, IAttachment rtcAttachment)
      throws TeamRepositoryException, IOException {
    String attachmentName = rtcAttachment.getName();
    if (attachmentName.startsWith("\\\\")) {
      System.out.println("***** I think I found a link: " + attachmentName);
    } else {
      Attachment att = attachmentStorage.createAttachment(workitemId, attachmentName);
      try (OutputStream out = att.openOutputStream()) {
        repo.contentManager().retrieveContent(rtcAttachment.getContent(), out, null);
      }
    }
  }
}
