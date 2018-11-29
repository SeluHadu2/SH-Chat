package com.seluhadu.shchat.models;import android.util.Log;import com.google.firebase.firestore.DocumentChange;import java.util.HashMap;public abstract class BaseMessage {    private static final String TAG = "BaseMessage";    private String userId;    private String receiverId;    private long messageId;    private long createdAt;    private long updatedAt;    private String msgType;    BaseMessage() {    }    public static BaseMessage build(DocumentChange documentChange, String senderId, String receiverId) {        try {            HashMap<String, Object> message = (HashMap<String, Object>) documentChange.getDocument().getData();            HashMap<String, Object> file = (HashMap<String, Object>) documentChange.getDocument().getData().get("file");            String type = documentChange.getDocument().get("msgType").toString();            byte var = -1;            if (type.equals("FILE")) {                var = 3;            } else if (type.equals("USMG")) {                var = 2;            }            String msgType;            String userId;            long messageId;            long createdAt;            switch (var) {                case 2:                    userId = message.get("userId").toString();                    msgType = message.get("msyType").toString();                    messageId = (long) message.get("messageId");                    createdAt = (long) message.get("createdAt");                    long massageUpdateAt = message.containsKey("updatedAt") ? (long) message.get("updatedAt") : 0L;                    return new UserMessage(UserMessage.build(senderId, receiverId, userId, msgType, createdAt, massageUpdateAt, messageId));                case 3:                    messageId = (long) message.get("messageId");                    createdAt = (long) message.get("createId");                    long fileUpdateAt = message.containsKey("updatedAt") ? (long) message.get("updatedAt") : 0L;                    String data = message.get("data").toString();                    String url = file.get("url").toString();                    String name = file.get("name").toString();                    String fileType = file.get("type").toString();                    String customType = file.get("customType").toString();                    int size = (int) file.get("size");                    return new FileMessage(FileMessage.build(senderId, receiverId, url, name, size, fileType, data, customType, createdAt, fileUpdateAt, messageId));            }        } catch (Exception e) {            Log.d(TAG, "Error: " + e.getMessage());        }        return null;    }    public BaseMessage(HashMap<String, Object> obj) {        this.userId = obj.get("userId").toString();        this.receiverId = obj.get("receiverId").toString();        this.msgType = obj.get("msgType").toString();        this.messageId = (long) obj.get("messageId");        this.createdAt = (long) obj.get("createdAt");        this.updatedAt = (long) obj.get("updatedAt");    }    HashMap<String, Object> toJson() {        HashMap<String, Object> obj = new HashMap<>();        obj.put("userId", this.messageId);        obj.put("receiverId", this.receiverId);        obj.put("msgType", this.msgType);        obj.put("createdAt", this.createdAt);        obj.put("updatedAt", this.updatedAt);        obj.put("messageId", this.messageId);        return obj;    }    public String getMsgType() {        return msgType;    }    public void setReceiverId(String receiverId) {        this.receiverId = receiverId;    }    public void setMsgType(String msgType) {        this.msgType = msgType;    }    public String getUserId() {        return userId;    }    public void setUserId(String userId) {        this.userId = userId;    }    public String getReceiverId() {        return receiverId;    }    public long getMessageId() {        return messageId;    }    public void setMessageId(long messageId) {        this.messageId = messageId;    }    public long getCreatedAt() {        return createdAt;    }    public void setCreatedAt(long createdAt) {        this.createdAt = createdAt;    }    public long getUpdatedAt() {        return updatedAt;    }    public void setUpdatedAt(long updatedAt) {        this.updatedAt = updatedAt;    }}