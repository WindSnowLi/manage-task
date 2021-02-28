/**
 * 
 * @param {*} urlParams 地址栏连接标识
 * @param {*} taskList  任务列表
 */
function _loadTaskLine(urlParams, taskList) {
  iniLeftMenu();
  document.getElementById("left_menu_task_tree").classList.add("menu-open");
  document.getElementById("left_menu_task").classList.add("active");
  document.getElementById("left_menu_" + urlParams).classList.add("active");
  let state = { title: '', url: window.location.href.split("?")[0] };
  history.pushState(state, '', window.location.href.split("?")[0] + '?url=' + urlParams);
  $("#main_content").load("../share/taskline.html", function () {
    document.getElementById('taskline_currentDate').innerHTML = getTimeStr();
    for (const key in Array.from(JSON.parse(taskList))) {
      document.getElementById('taskline_start_lable').insertAdjacentHTML("afterend", buildTaskLineNode(Array.from(JSON.parse(taskList))[key]));
    }
  });
}

function loadAcademyTaskLine() {
  findAcademyAllTasksJson(_loadTaskLine, "academyTask")
}


//加载任务线
function loadClassTaskLine() {
  findClazzAllTasksJson(_loadTaskLine, "classTask")
}

/**
 * 加载详情页
 * @param {*} taskId 任务ID
 */
function toTaskDetail(taskId) {
  $('#main_content').load("../share/taskdetail.html", function () {
    setServerUrlTaskId(taskId);
    _requestPost({ "taskId": taskId }, getUrl() + "/task/findTaskJson", function (params) {
      let state = { title: '', url: window.location.href.split("?")[0] };
      history.pushState(state, '', window.location.href.split("?")[0] + '?url=' + getQueryString('url') + '&taskId=' + taskId + '&userId=' + getUserId());

      let taskJson = JSON.parse(params);
      document.getElementById('taskDetail_title').innerText = taskJson.taskTitle;
      document.getElementById('taskDetail_content').innerText = taskJson.taskContent;

      _requestPost({ "userId": taskJson.userId }, getUrl() + "/user/findIdUserJson", function (params) {
        let userTask = JSON.parse(params);
        document.getElementById('task_userId').innerText = userTask.userNickname;
      });

      if (taskJson.lastChangeUserId != 0) {
        _requestPost({ "userId": taskJson.lastChangeUserId }, getUrl() + "/user/findIdUserJson", function (params) {
          let userTask = JSON.parse(params);
          document.getElementById('task_lastChangeUserId').innerText = userTask.userNickname;
        });
      }

      _requestPost({ "taskId": taskId, "currentPage": 1, "pageSize": 10 }, getUrl() + "/task/getTaskCompleteRecordPageJson", function (params) {
        let taskRecordList = Array.from(JSON.parse(params));
        for (const key in taskRecordList) {
          let userNode = '<div class="user-block"> ' +
            ' <img class="img-circle img-bordered-sm" src="${USERHEADPICTURE}"' +
            '   alt="user image">' +
            ' <span class="username">' +
            '  <a href="#">${USERNICKNAME}</a>' +
            ' </span>' +
            ' <span class="description">${SUBMISSIONTIME}</span>' +
            ' </div>';

          let student = taskRecordList[key].student;
          userNode = userNode.replace(/\$\{USERHEADPICTURE\}/g, student.userHeadPicture);
          userNode = userNode.replace(/\$\{USERNICKNAME\}/g, student.userNickname);
          userNode = userNode.replace(/\$\{SUBMISSIONTIME\}/g, new Date(taskRecordList[key].submissionTime).format("Y-m-d H:i:s"));
          document.getElementById('task_already_submit_user').insertAdjacentHTML("afterbegin", userNode);
        }
      });

    });

    _requestPost({ "taskId": taskId }, getUrl() + "/task/getTaskCompleteUserCountJson", function (params) {
      document.getElementById('taskDetail_complete').innerText = params;
    });

    _requestPost({ "taskId": taskId }, getUrl() + "/task/getTaskUnfinishedCountJson", function (params) {
      document.getElementById('taskDetail_unfinished').innerText = params;
    });
    _requestPost({ "taskId": taskId }, getUrl() + "/task/findTaskJson", function (params) {
      let taskJson = JSON.parse(params);
      let present = new Date();
      let time_difference = new Date() - new Date(taskJson.taskEndDate);
      if (present > new Date(taskJson.taskEndDate)) {
        document.getElementById('taskDetail_surplus_time').innerText = "已经截至";
      } else if (present < new Date(taskJson.taskEndDate) && present > new Date(taskJson.taskStartDate)) {
        time_difference = new Date(taskJson.taskEndDate) - new Date();
        //计算出相差天数
        let days = Math.floor(time_difference / (24 * 3600 * 1000));
        //计算出小时数
        let leave1 = time_difference % (24 * 3600 * 1000);
        //计算天数后剩余的毫秒数
        let hours = Math.floor(leave1 / (3600 * 1000));
        //计算相差分钟数
        let leave2 = leave1 % (3600 * 1000);
        //计算小时数后剩余的毫秒数
        let minutes = Math.floor(leave2 / (60 * 1000));
        //计算相差秒数
        let leave3 = leave2 % (60 * 1000);
        //计算分钟数后剩余的毫秒数
        let seconds = Math.round(leave3 / 1000);
        document.getElementById('taskDetail_surplus_time').innerText = days + "天 " + hours + "小时 " + minutes + " 分钟" + seconds + " 秒";
      }

    });
  });
}

/**
 * 创建任务线节点
 * @param {*} taskJson 任务对象
 */
function buildTaskLineNode(taskJson) {
  let taskNode = '<div>' +
    ' <i class="fas fa-tasks bg-blue"></i>' +
    ' <div class="timeline-item">' +
    '   <span class="time">截止时间<i class="fas fa-clock"></i>${TASKENDTIME}</span>' +
    '   <span class="time">开始时间<i class="fas fa-clock"></i>${TASKSTARTTIME}</span>' +
    '   <h3 class="timeline-header"><a href="#">${TASKSUBJECT}</a></h3>' +
    '   <div class="timeline-body">' +
    '      ${TASKCONTENT}' +
    '  </div>' +
    '   <div class="timeline-footer">' +
    '     <a value="${TASKSHOWID}" onclick="toTaskFinish(this)" href="javascript:void(0)" class="btn btn-primary btn-sm">详情</a>' +
    '    </div>' +
    '  </div>' +
    '</div>';
  let timeLable = '<div class="time-label">' +
    '<span class="bg-green">${LABLETIME}</span>' +
    '</div>';

  taskNode = taskNode.replace(/\$\{TASKSTARTTIME\}/g, new Date(taskJson.taskStartDate).format('Y-m-d H:i:s'));
  taskNode = taskNode.replace(/\$\{TASKENDTIME\}/g, new Date(taskJson.taskEndDate).format('Y-m-d H:i:s'));
  taskNode = taskNode.replace(/\$\{TASKSUBJECT\}/g, taskJson.taskTitle);
  taskNode = taskNode.replace(/\$\{TASKCONTENT\}/g, taskJson.taskContent);
  taskNode = taskNode.replace(/\$\{TASKSHOWID\}/g, taskJson.taskId);
  timeLable = timeLable.replace(/\$\{LABLETIME\}/g, new Date(taskJson.taskCreateDate).format('Y-m-d H:i:s'));
  return timeLable + taskNode;
}

/**
 * 去任务详情页
 * @param {*} element readmore元素
 */
function toTaskFinish(element) {
  toTaskDetail(element.getAttribute("value"));
}