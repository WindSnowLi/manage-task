/**
 * 加载添加班级任务界面
 */
function loadManageAddClazzTask() {
    iniLeftMenu();
    let state = { title: '', url: window.location.href.split("?")[0] };
    history.pushState(state, '', window.location.href.split("?")[0] + '?url=manageAddClassTask');
    document.getElementById("left_menu_manage_addTask_tree").classList.add("menu-open");
    document.getElementById("left_menu_manage_addTask").classList.add("active");
    document.getElementById("left_menu_manage_addClassTask").classList.add("active");
    $('#main_content').load("../share/addtask.html", function () {
        document.getElementById('addTask_from_button').onclick = addClazzTaskPostForm;
        document.getElementById('addTask_h3').innerHTML = '添加班级任务';
    });
}


/**
 * 加载添加学院任务界面
 */
function loadManageAddAcademyTask() {
    iniLeftMenu();
    let state = { title: '', url: window.location.href.split("?")[0] };
    history.pushState(state, '', window.location.href.split("?")[0] + '?url=manageAddAcademyTask');
    document.getElementById("left_menu_manage_addTask_tree").classList.add("menu-open");
    document.getElementById("left_menu_manage_addTask").classList.add("active");
    document.getElementById("left_menu_manage_addAcademyTask").classList.add("active");
    $('#main_content').load("../share/addtask.html", function () {
        document.getElementById('addTask_from_button').onclick = addAcademyTaskPostForm;
        document.getElementById('addTask_h3').innerHTML = '添加学院任务';
    });
}



/**
 * 添加班级任务
 */
function addClazzTaskPostForm() {
    let obj = serializeJsonForm("addTask_form");
    obj['taskPowerId'] = getStudentClazzId();
    obj['taskPower'] = getTaskSign("CLAZZ");
    _addTaskPostForm(obj);
}

/**
 * 添加学院任务
 */
function addAcademyTaskPostForm() {
    let obj = serializeJsonForm("addTask_form");
    obj['taskPowerId'] = getStudentAcademyId();
    obj['taskPower'] = getTaskSign("ACADEMY");
    _addTaskPostForm(obj);
}


/**
 * 添加任务
 * @param {JSON} obj 
 */
function _addTaskPostForm(obj) {
    let reservationTime = obj['reservationTime'];
    obj['taskStartDate'] = reservationTime.split(' 至 ')[0];
    obj['taskEndDate'] = reservationTime.split(' 至 ')[1];
    delete obj.reservationTime;
    if (obj['taskTitle'] == '' || obj['taskContent'] == '') {
        alert("标题内容不可为空！")
        return false;
    }
    _submitPost(addUserJsonId(obj), getUrl() + "/task/addTaskJson", function (results) {
        if (results.code == 1) {
            alert("添加成功");
            document.getElementById("addTask_form").reset();
        } else {
            alert("添加失败");
        }
    });
}