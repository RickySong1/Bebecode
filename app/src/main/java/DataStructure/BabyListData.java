package DataStructure;

/**
 * Created by SuperSong on 2017-03-26.
 */

public class BabyListData {
    private int img;
    private String name;
    private String status;
    private String id;
    private String month;

    public BabyListData(int img, String name, String status,String id , String month) {
        this.img = img;
        this.name = name;
        this.status = status;
        this.id = id;
        this.month = month;
    }

    public void setImg(int img) {
        this.img = img;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setId(String id) { this.id = id; }
    public void setMonth(String month) { this.month = month; }

    public String getId() { return id; }
    public int getImg() {
        return img;
    }
    public String getName() {
        return name;
    }
    public String getStatus() {
        return status;
    }
    public String getMonth() { return month; }
}
