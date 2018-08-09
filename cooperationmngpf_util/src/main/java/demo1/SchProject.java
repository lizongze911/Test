package demo1;

import java.io.Serializable;

public class SchProject implements Serializable{
	/*private static final long serialVersionUID=*/
	/**
     * uuid
     */
    private String id;
 
	/**
     * 文件名字
     */
    private String name;
    /**
     * 版本号 默认0
     */
    private Integer version;
    /**
     * 作者
     */
    private String author;

    //这里写set和get
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
    //这里写toStirng方法
	   
		@Override
		public String toString() {
			return "SchProject [id=" + id + ", name=" + name + ", version=" + version + ", author=" + author + "]";
		}
}
