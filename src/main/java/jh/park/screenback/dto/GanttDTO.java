package jh.park.screenback.dto;

import java.time.LocalDateTime;

public class GanttDTO {
private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private int progress;
    private LocalDateTime editTime;
    private Long editUserId;
    private Long createdUserId;
    private Long userGroupId;
    private Long fileId;

    public GanttDTO() {
    }

    public GanttDTO(Long id, String title, LocalDateTime start, LocalDateTime end, int progress, LocalDateTime editTime, Long editUserId, Long createdUserId, Long userGroupId, Long fileId) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.progress = progress;
        this.editTime = editTime;
        this.editUserId = editUserId;
        this.createdUserId = createdUserId;
        this.userGroupId = userGroupId;
        this.fileId = fileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDateTime getEditTime() {
        return editTime;
    }

    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
    }

    public Long getEditUserId() {
        return editUserId;
    }

    public void setEditUserId(Long editUserId) {
        this.editUserId = editUserId;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
