package model;

/**
 * Created by zyuki on 12/21/2015.
 */
public class DataItem {
    private String id;
    private String dataTitle;
    private String dataContent;
    private long timestamp;

    //Getters
    public String getId() {return id;}
    public String getDataTitle() {return dataTitle;}
    public String getDataContent() {return dataContent;}
    public long getTimestamp() {return timestamp;}

    //Setter
    public void setId(String id) {this.id = id;}
    public void setDataTitle(String dataTitle) {this.dataTitle = dataTitle;}
    public void setDataContent(String dataContent) {this.dataContent = dataContent;}
    public void setTimestamp(long timestamp) {this.timestamp = timestamp;}
}
