/**
 * 管理班级任务界面
 */
function loadManageClazzTask() {
  iniLeftMenu();
  let state = { title: '', url: window.location.href.split("?")[0] };
  history.pushState(state, '', window.location.href.split("?")[0] + '?url=manageClassTask');
  document.getElementById("left_menu_manage_task_tree").classList.add("menu-open");
  document.getElementById("left_menu_manage_task").classList.add("active");
  document.getElementById("left_menu_manage_classTask").classList.add("active");
  $("#main_content").load("../share/tasktable.html", function () {
    getClazzTaskListCount(
      function (taskCount) {
        $(".pages").createPage({
          pageNum: Number(taskCount) % 10 == 0 ? (Number(taskCount) / 10) : (Math.ceil(Number(taskCount) / 10)),
          current: 1,
          backfun: function (e) {
            requestClazzTaskListPage(e.current, 10, loadTaskTableNode);
          }
        })
      });
    requestClazzTaskListPage(1, 10, loadTaskTableNode);
  });
}

/**
 * 加载管理学院任务界面
 */
function loadManageAcademyTask() {
  iniLeftMenu();
  let state = { title: '', url: window.location.href.split("?")[0] };
  history.pushState(state, '', window.location.href.split("?")[0] + '?url=manageAcademyTask');
  document.getElementById("left_menu_manage_task_tree").classList.add("menu-open");
  document.getElementById("left_menu_manage_task").classList.add("active");
  document.getElementById("left_menu_manage_academyTask").classList.add("active");
  $("#main_content").load("../share/tasktable.html", function () {
    getAcademyTaskListCount(
      function (taskCount) {
        $(".pages").createPage({
          pageNum: Number(taskCount) % 10 == 0 ? (Number(taskCount) / 10) : (Math.ceil(Number(taskCount) / 10)),
          current: 1,
          backfun: function (e) {
            requestAcademyTaskListPage(e.current, 10, loadTaskTableNode);
          }
        })
      });
    requestAcademyTaskListPage(1, 10, loadTaskTableNode);
  });
}


/**
 * 插入任务完成节点
 * @param {list} taskListJson 任务list
 */
function loadTaskTableNode(taskListJson) {
  let taskList = Array.from(JSON.parse(taskListJson));
  for (const key in taskList) {
    document.getElementById('tasktable_node_container').insertAdjacentHTML("afterbegin", buildTaskTableNode(taskList[key]));
    loadTaskCompleteHead(insertTaskCompleteHead, taskList[key].taskId);
  }
}

/**
 * 插入任务完成头像节点
 * @param {int} taskId 任务Id
 * @param {taskRecord} taskRecordJson 报告条对象
 */
function insertTaskCompleteHead(taskId, taskRecordJson) {

  let taskRecordList = Array.from(JSON.parse(taskRecordJson));
  for (const key in taskRecordList) {
    let taskCompleteHead = '<li class="list-inline-item">' +
      '<img alt="Avatar" class="table-avatar" src="${TASKCOMPLETEHEAD}">' +
      '</li>';
    taskCompleteHead = taskCompleteHead.replace(/\$\{TASKCOMPLETEHEAD\}/g, taskRecordList[key].student.userHeadPicture);
    document.getElementById('task_complete_head_' + taskId).insertAdjacentHTML("beforeend", taskCompleteHead);
  }
}


/**
 * 构建任务表节点
 * @param {task} taskJson 任务对象
 */
function buildTaskTableNode(taskJson) {
  let taskTableNode = '<tr id="taskTable_tr_${TASKID}">' +
    '    <td>' +
    '        #' +
    '    </td>' +
    '    <td>' +
    '        <a>' +
    '            ${TASKNAME}' +
    '        </a>' +
    '        <br/>' +
    '        <small>' +
    '            创建 ${TASKCREATEDATE}' +
    '        </small>' +
    '    </td>' +
    '    <td>' +
    '        <ul id="task_complete_head_${TASKID}" class="list-inline">' +
    '        </ul>' +
    '    </td>' +
    '    <td class="project_progress">' +
    '        <div class="progress progress-sm">' +
    '            <div class="progress-bar bg-green" role="progressbar" aria-volumenow="${TASKCOMPLETEPROGRESS}"' +
    '                aria-volumemin="0" aria-volumemax="100" style="width: ${TASKCOMPLETEPROGRESS}%">' +
    '            </div>' +
    '        </div>' +
    '        <small>' +
    '            ${TASKCOMPLETEPROGRESS}% 已完成' +
    '        </small>' +
    '    </td>' +
    '    <td class="project-state">' +
    '        <span class="badge ${TASKSTATUSCOLOR}">${TASKSTATUS}</span>' +
    '    </td>' +
    '    <td class="project-actions text-right">' +
    '        <a value="${TASKID}" onclick="managerToView(this)" class="btn btn-primary btn-sm" href="#">' +
    '            <i class="fas fa-folder">' +
    '            </i>' +
    '            文件' +
    '        </a>' +
    '        <a value="${TASKID}" onclick="managerToEdit(this)" class="btn btn-info btn-sm" href="#">' +
    '            <i class="fas fa-pencil-alt">' +
    '            </i>' +
    '            编辑' +
    '        </a>' +
    '        <a value="${TASKID}" onclick="managerToDelete(this)" class="btn btn-danger btn-sm" href="#">' +
    '            <i class="fas fa-trash">' +
    '            </i>' +
    '            删除' +
    '        </a>' +
    '    </td>' +
    '</tr>';
  taskTableNode = taskTableNode.replace(/\$\{TASKID\}/g, taskJson.taskId);
  taskTableNode = taskTableNode.replace(/\$\{TASKNAME\}/g, taskJson.taskTitle);
  taskTableNode = taskTableNode.replace(/\$\{TASKCREATEDATE\}/g, new Date(taskJson.taskCreateDate).format('Y-m-d H:i:s'));
  taskTableNode = taskTableNode.replace(/\$\{TASKCOMPLETEPROGRESS\}/g, taskJson.completeRate);
  if (taskJson.taskStartDate > new Date()) {
    taskTableNode = taskTableNode.replace(/\$\{TASKSTATUS\}/g, "未开始");
    taskTableNode = taskTableNode.replace(/\$\{TASKSTATUSCOLOR\}/g, "badge-warning");
  } else if (taskJson.taskEndDate < new Date()) {
    taskTableNode = taskTableNode.replace(/\$\{TASKSTATUS\}/g, "已经结束");
    taskTableNode = taskTableNode.replace(/\$\{TASKSTATUSCOLOR\}/g, "badge-danger");
  } else {
    taskTableNode = taskTableNode.replace(/\$\{TASKSTATUS\}/g, "正在进行");
    taskTableNode = taskTableNode.replace(/\$\{TASKSTATUSCOLOR\}/g, "badge-success");
  }
  return taskTableNode;
}

/**
 * 查看提交详情
 * @param {element} obj 
 */
function managerToView(obj) {
  $("#main_content").load("../share/downfile.html", function () {

    _requestPost({ "taskId": obj.getAttribute("value") }, getUrl() + "/file/getTaskFileListJson", function (params) {
      let taskRecordList = Array.from(JSON.parse(params));
      for (const key in taskRecordList) {
        document.getElementById('filetable_node_container').insertAdjacentHTML("afterbegin", loadFileTableNode(taskRecordList[key]));
      }
    })
  });
}
/**
 * 编辑任务
 * @param {element} obj 
 */
function managerToEdit(obj) {
  $("#main_content").load("../share/edittask.html", function () {
    _requestPost({ "taskId": obj.getAttribute("value") }, getUrl() + "/task/findTaskJson", function (task) {
      taskJson = JSON.parse(task);
      document.getElementById('editTask_inputName').value = taskJson.taskTitle;
      document.getElementById('editTask_inputDescription').value = taskJson.taskContent;
      // document.getElementById('editTask_reservationTime').value =taskJson.taskReservationTime;
      document.getElementById('editTask_button').value = taskJson.taskId;
      console.log(new Date(taskJson.taskStartDate).format('Y-m-d H:i:s'));
      $("#editTask_reservationTime").data('daterangepicker').setStartDate(new Date(taskJson.taskStartDate).format('Y-m-d H:i:s'));
      $('#editTask_reservationTime').data('daterangepicker').setEndDate(new Date(taskJson.taskEndDate).format('Y-m-d H:i:s'));
    })
  });
}

/**
 * 
 * @param {element} obj 
 */
function managerToDelete(obj) {
  deleteTaskId(obj.getAttribute('value'));
}
/**
 * 删除任务
 * @param {int} taskId 
 */
function deleteTaskId(taskId) {
  _submitPost({ "taskId": taskId }, getUrl() + "/task/deleteTaskJson", function (results) {
    if (results.code == 1) {
      alert(results.msg);
      document.getElementById("taskTable_tr_" + taskId).remove();
    } else {
      alert("删除失败")
    }
  });
}