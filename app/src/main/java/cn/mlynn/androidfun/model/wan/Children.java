/**
 * @ProjectName: AndroidFun
 * @Package: cn.mlynn.androidfun.model
 * @ClassName: Children
 * @Description: //TODO
 * @Author: Michael Lynn
 * @CreateDate: 2020/7/10 18:56
 */
package cn.mlynn.androidfun.model.wan;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.List;

import cn.mlynn.androidfun.model.BaseEntity;
import cn.mlynn.androidfun.typeconverter.ChildrenListTypeConverter;

@Entity(primaryKeys = {"id", "key"})
public class Children extends BaseEntity {

    @TypeConverters(ChildrenListTypeConverter.class)
    private List<Children> children;
    private int courseid;
    private int id;
    private String name;
    private int order;
    private int parentchapterid;
    private boolean usercontrolsettop;
    private int visible;

    public void setChildren(List<Children> children) {
        this.children = children;
    }
    public List<Children> getChildren() {
        return children;
    }


    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }
    public int getCourseid() {
        return courseid;
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


    public void setOrder(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }


    public void setParentchapterid(int parentchapterid) {
        this.parentchapterid = parentchapterid;
    }
    public int getParentchapterid() {
        return parentchapterid;
    }


    public void setUsercontrolsettop(boolean usercontrolsettop) {
        this.usercontrolsettop = usercontrolsettop;
    }
    public boolean getUsercontrolsettop() {
        return usercontrolsettop;
    }


    public void setVisible(int visible) {
        this.visible = visible;
    }
    public int getVisible() {
        return visible;
    }
}
