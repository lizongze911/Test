package demo1;

import org.springframework.expression.spel.ast.Projection;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018/8/6.
 */

public class MppUtil {

	/**
	 * 顶级的父类Id
	 **/
	private static final int TOP_PARENTID = 0;

	/**
	 * 顶级的层次
	 **/
	private static final int TOP_LEVEL = 1;

	/**
	 * 导出生成mpp文件存放的路径
	 **/
	private static final String FILE_PATH = "D:/tempFileSavePath.mpp";

	private static ProjectFile readProject(String filename) throws FileNotFoundException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		is.mark(0);
		ProjectFile mpx = null;
		try {
			mpx = new MPXReader().read(is);

		} catch (Exception ex) {
			// TODO: handle exception
			try {
				is.reset();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if (mpx == null) {
			try {
				mpx = new MPPReader().read(is);
			} catch (Exception ex) {
				// TODO: handle exception
				try {
					is.reset();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		if (mpx == null) {
			try {
				mpx = new MSPDIReader().read(is);
			} catch (Exception ex) {
				// TODO: handle exception
			}
		}
		return mpx;
	}

	public static List<SchProjectTask> getTaskList(String filename) throws FileNotFoundException {
		ProjectFile file = readProject(filename);
		List<Task> tasks = file.getChildTasks();
		List<SchProjectTask> schProjectTaskList = new ArrayList<SchProjectTask>();
		if (!tasks.isEmpty()) {
			Task msTask = tasks.get(TOP_PARENTID);
			schProjectTaskList = listHierarchy(msTask, TOP_PARENTID);
		}
		return schProjectTaskList;
	}

	private static List<SchProjectTask> listHierarchy(Task msTask, int parentId) {
		// TODO Auto-generated method stub
		List<Task> childTasks = msTask.getChildTasks();
		List<SchProjectTask> schProjectTaskList = new ArrayList<SchProjectTask>();
		SchProjectTask schProjectTask = null;
		if (!childTasks.isEmpty()) {
			for (Task task : childTasks) {
				schProjectTaskList.add(getTaskBean(schProjectTask, task, parentId));
				schProjectTaskList.addAll(listHierarchy(task, task.getID()));
			}
		}
		return schProjectTaskList;
	}

	private static SchProjectTask getTaskBean(SchProjectTask schProjectTask, Task task, Integer parentId) {
		// TODO Auto-generated method stub
		schProjectTask = new SchProjectTask();
		schProjectTask.setRecordId(task.getID().toString());
		schProjectTask.setUniqueId(task.getUniqueID().toString());
		schProjectTask.setParentId(parentId.toString());
		schProjectTask.setName(task.getName());
		schProjectTask.setDuration(task.getDuration().getDuration());
		schProjectTask.setDurationTimeUnit(task.getDuration().getUnits().getName());
		schProjectTask.setStartTime(task.getStart());
		schProjectTask.setFinishTime(task.getFinish());
		schProjectTask.setPercentageComplete(task.getPercentageComplete());
		schProjectTask.setLevel(task.getOutlineLevel());

		List<String> preLists = new ArrayList<String>();

		List<Relation> predecessors = task.getPredecessors();
		if (predecessors != null && predecessors.isEmpty() == false) {
			for (Relation relation : predecessors) {
				Task targeTask = relation.getTargetTask();
				Integer targetTaskRecordId = targeTask.getID();
				Integer targetTaskUniqeId = targeTask.getUniqueID();
				String m_type = relation.getType().toString();
				String m_lag = relation.getLag().toString();

				String predecessor = targetTaskRecordId + ";" + targetTaskUniqeId + ";" + m_type + ";" + m_lag;
				preLists.add(predecessor);
			}
		}
		schProjectTask.setPredecessors(listToString(preLists));
		return schProjectTask;
	}

	public static String listToString(List list) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}
/*	public static void createMappFile(List<TaskBean> taskBeanList) throws Exception {
		File file=new File(FILE_PATH);
		if(file.exists()) {
			file.delete();
		}
		if(taskBeanList!=null&&taskBeanList.size()>0) {
			ActiveXComponent app=null;
			try {
				app=new ActiveXComponent("MSProject.Application");
				app.setProperty("Visible", new Variant(false));
				Dispatch projects=app.getProperty("Projects").toDispatch();
				Dispatch project=Dispatch.call(projects, "Add").toDispatch();
				Dispatch tasks=Dispatch.get(project, "Tasks").toDispatch();
				
				TaskBean topTaskBean=getTopTaskBean(taskBeanList);
				createTreeTable(tasks, topTaskBean, TOP_LEVEL, taskBeanList);
                //另存为
                Dispatch.invoke(project, "SaveAs", Dispatch.Method, new Object[]{FILE_PATH, new Variant(0)}, new int[1]);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("aaaaaaaaaa");
            } finally {
                if (app != null)
                    app.invoke("Quit", new Variant[]{});
                }
			}
	}  
	*//**
     * 创建树形结构
     *
     * @param tasks        任务集合
     * @param taskBean     任务Bean
     * @param level        层次
     * @param taskBeanList 任务列表
     *//*
    private static void createTreeTable(Dispatch tasks, TaskBean taskBean, int level, List<TaskBean> taskBeanList) {
        Dispatch task = Dispatch.call(tasks, "Add").toDispatch();
        setTaskValue(task, taskBean, level);
        List<TaskBean> sonTaskBeanList = getSonTaskBean(taskBeanList, taskBean);
        if (!sonTaskBeanList.isEmpty()) {
            for (TaskBean sonTaskBean : sonTaskBeanList) {
                createTreeTable(tasks, sonTaskBean, level + 1, taskBeanList);
            }
        }
    }
    *//**
     * 获取所有的子任务
     *
     * @param taskBeanList   任务列表
     * @param parentTaskBean 父级任务Bean
     * @return
     *//*
    private static List<TaskBean> getSonTaskBean(List<TaskBean> taskBeanList, TaskBean parentTaskBean) {
        List<TaskBean> sonTaskBeanList = new ArrayList<TaskBean>();
        for (TaskBean taskBean : taskBeanList) {
            if (taskBean.getParentId() == parentTaskBean.getId()) {
                sonTaskBeanList.add(taskBean);
            }
        }
        return sonTaskBeanList;
    }

    *//**
     * 获取顶级任务
     *
     * @param taskBeanList 任务列表
     * @return
     *//*
    private static TaskBean getTopTaskBean(List<TaskBean> taskBeanList) {
        for (TaskBean taskBean : taskBeanList) {
            if (taskBean.getParentId() == TOP_PARENTID)
                return taskBean;
        }
        return null;
    }

    *//**
     * 给任务设置属性
     *
     * @param task     任务指针
     * @param taskBean 任务Bean
     * @param level    层次
     *//*
    private static void setTaskValue(Dispatch task, TaskBean taskBean, int level) {
        Dispatch.put(task, "Name", taskBean.getName());
        Dispatch.put(task, "Start", taskBean.getStartTime());
        Dispatch.put(task, "Finish", taskBean.getFinishTime());
        Dispatch.put(task, "OutlineLevel", level);
        Dispatch.put(task, "ResourceNames", taskBean.getResource());
    }
*/
    /**
     * 获取任务中的资源
     *
     * @param task 任务
     * @return
     */
    private static String listTaskRes(Task task) {
        StringBuffer buf = new StringBuffer();
        List<ResourceAssignment> assignments = task.getResourceAssignments();//获取任务资源列表
        if (assignments != null && !assignments.isEmpty()) {
            ResourceAssignment assignment = (ResourceAssignment) assignments.get(0);//只获取第一个资源
            Resource resource = assignment.getResource();
            if (resource != null)
                buf.append(resource.getName());
        }
        return buf.toString();
    }

    public static void main(String[] args) throws Exception {
        List<SchProjectTask> taskBeanList = MppUtil.getTaskList("D:/CEBIM使用.mpp");
        System.out.println(taskBeanList.size());
        for (SchProjectTask task : taskBeanList) {
            System.out.println(task);
        }
    }
}
