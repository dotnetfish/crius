

package cn.cloudartisan.crius.client.model;

import java.io.Serializable;

public class Message implements Serializable {
  private String content;
  private String file;
  private String fileType;
  private String mid;
  private String receiver;
  private String sender;
  private static final long serialVersionUID = 0x1L;
  private long timestamp;
  private String title;
  private String type;
  private String format = "txt";

  public Message() {
    timestamp = System.currentTimeMillis();
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    buffer.append("<message>");
    buffer.append("<mid>").append(mid).append("</mid>");
    buffer.append("<type>").append(type).append("</type>");
    buffer.append("<title>").append(getTitle() == null ? "" : getTitle()).append("</title>");
    buffer.append("<content><![CDATA[").append(getContent() == null ? "" : getContent()).append("]]></content>");
    buffer.append("<file>").append(getFile() == null ? "" : getFile()).append("</file>");
    buffer.append("<fileType>").append(getFileType() == null ? "" : getFileType()).append("</fileType>");
    buffer.append("<sender>").append(getSender() == null ? "" : getSender()).append("</sender>");
    buffer.append("<receiver>").append(getReceiver() == null ? "" : getReceiver()).append("</receiver>");
    buffer.append("<format>").append(getFormat() == null ? "" : getFormat()).append("</format>");
    buffer.append("<timestamp>").append(timestamp).append("</timestamp>");
    buffer.append("</message>");
    return buffer.toString();
  }

  public String toXmlString() {
    return toString();
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    mid = mid;
  }
}
