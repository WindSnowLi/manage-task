/**
 * 首页
 */
$(function () {
  document.getElementById('left_menu_userHead').src = getUserHeadPicture();
  setElementValue('left_menu_userName', getUserName());
  //加载仪表盘
  if (getUserPower() > 0) {
    document.getElementById('left_menu_task_tree_back').insertAdjacentHTML("beforebegin", '<div id="left_menu_dashboard_tree_div"></div>');
    $("#left_menu_dashboard_tree_div").load("../element/clazz_dashboard_left_tree.html", function () {
      if (getQueryString('url') == "clazzDashboard") {
        loadClazzDashboard();
      }
    });
  }
  //加载个人界面和任务时间线
  if (getUserPower() >=0) {
    document.getElementById('left_menu_task_tree_back').insertAdjacentHTML("beforebegin", '<div id="left_menu_myself_tree_div"></div>');
    $("#left_menu_myself_tree_div").load("../element/myself-left_tree.html", function () {
      if (getQueryString('url') == "profile") {
        loadProfile();
      }
    });
    document.getElementById('left_menu_task_tree_back').insertAdjacentHTML("beforebegin", '<div id="left_menu_task_tree_div"></div>');
    $("#left_menu_task_tree_div").load("../element/task_left_tree.html", function () {
      if (getQueryString('url') == "classTask") {
        loadClassTaskLine();
      } else if (getQueryString('url') == "academyTask") {

        if (getQueryString('taskId') != null && getQueryString('userId') != null) {
          toTaskDetail(getQueryString('taskId'));
        } else {
          loadAcademyTaskLine();
        }
      }
    });
  }
  //权限大于0加载管理任务界面
  if (getUserPower() > 0) {
    document.getElementById('left_menu_task_tree_back').insertAdjacentHTML("beforebegin", '<div id="left_menu_manage_task_tree_div"></div>');
    $("#left_menu_manage_task_tree_div").load("../element/manage_task_left_tree.html", function () {
      if (getQueryString('url') == "manageClassTask") {
        loadManageClazzTask();
      } else if (getQueryString('url') == "manageAcademyTask") {
        loadManageAcademyTask();
      }
    });
    document.getElementById('left_menu_task_tree_back').insertAdjacentHTML("beforebegin", '<div id="left_menu_manage_addtask_tree_div"></div>');
    $("#left_menu_manage_addtask_tree_div").load("../element/manage_addTask_left_tree.html", function () {
      if (getQueryString('url') == "manageAddClassTask") {
        loadManageAddClazzTask();
      } else if (getQueryString('url') == "manageAddAcademyTask") {
        loadManageAddAcademyTask();
      }
    });
  }
});

/**
 * 初始化左侧导航栏
 */
function iniLeftMenu() {
  Array.from(document.getElementsByClassName('left_menu_button_active')).forEach(element => {
    if (element.classList.contains('active')) {
      element.classList.remove('active');
    }
  });
}