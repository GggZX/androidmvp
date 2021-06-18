package com.example.myapplication.utils.updateapk;

/**
 * 类描述：
 * 时  间：2017/10/27
 * 修改备注：
 */
public class EventMessage {
    public final static int Exitapp = 0x11;
    public final static int CheckApp = 0x12;
    public final static int POST_ID=0x13;
    int MessageType;
    boolean DownLoading = false;
    long downId;
    int downloadPercent;

    public int getDownloadPercent() {
        return downloadPercent;
    }

    public void setDownloadPercent(int downloadPercent) {
        this.downloadPercent = downloadPercent;
    }

    public long getDownId() {
        return downId;
    }

    public void setDownId(long downId) {
        this.downId = downId;

    }

    public EventMessage(int messageType,int downloadPercent){
        this.downloadPercent = downloadPercent;
        this.MessageType=messageType;
    }

    public EventMessage(int messageType, boolean downLoading) {
        MessageType = messageType;
        DownLoading = downLoading;
    }

    public EventMessage(int messageType) {
        MessageType = messageType;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        MessageType = messageType;
    }

    public boolean isDownLoading() {
        return DownLoading;
    }

    public void setDownLoading(boolean downLoading) {
        DownLoading = downLoading;
    }
}
