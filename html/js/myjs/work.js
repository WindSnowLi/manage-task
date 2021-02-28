/**
 * 服务地址
 */
function getUrl() {
    return 'http://127.0.0.1:43999';
}

function setToken(token) {
    localStorage.setItem("token", token);
}

function setUser(user) {
    localStorage.setItem("user", user);
}

function getUserToken() {
    return localStorage.getItem("token");
}

function getUser() {
    return JSON.parse(localStorage.getItem("user"));
}

function getUserName() {
    return getUser().userName;
}

function getUserHeadPicture() {
    return getUser().userHeadPicture;
}

function getUserNumber() {
    return getUser().userNumber;
}

function getUserEmail() {
    return getUser().userEmail;
}

function getUserNickname() {
    return getUser().userNickname;
}

function getUserCreateTime() {
    return getUser().userCreateTime;
}

function getUserId() {
    return getUser().userId;
}


function getUserPower() {
    return getUser().userPower;
}


function addUserJsonId(obj) {
    obj['userId'] = getUserId();
    return obj;
}
function getStudentClazzId() {
    if (getUser().hasOwnProperty("studentClazzId")) {
        return getUser().studentClazzId;
    }
    return null;
}

function getStudentAcademyId() {
    if (getUser().hasOwnProperty("studentAcademyId")) {
        return getUser().studentAcademyId;
    }
    return null;
}

function getTaskSign(type) {
    let returnType;
    switch (type) {
        case "CLAZZ":
            returnType = "CLAZZ";
            break;
        case "MAJOR":
            returnType = "MAJOR";
            break;
        case "ACADEMY":
            returnType = "ACADEMY";
            break;
        default:
            returnType = "CLAZZ";
            break;
    }
    return returnType;
}

/**
 * 设置控件显示
 * @param {any} elementID 控件ID
 * @param {any} value  显示值
 */
function setElementValue(elementID, value) {
    document.getElementById(elementID).innerText = value;
}


/**
 * 
 * @param {any} params 请求参数
 * @param {url} url 请求地址
 * @param {function} callback 回调函数 
 * @param {*} callbackParams 回调函数参数
 */
function _jsonPost(params, url, callback, callbackParams) {
    params['token'] = getUserToken();
    $.ajax({
        type: "POST",
        headers: {
            'token': getUserToken()
        },
        url: url,
        dataType: "json",
        async: true,
        contentType: "application/json",
        data: JSON.stringify(params),//将对象转为json字符串
        success: function (results) {
            if (results.code == 1) {
                callback(callbackParams, results.content);
            } else {
                alert(results.msg);
            }
        },
        error: function (results) {
            alert("请求错误")
        }
    });
}

/**
 * POST请求返回内容
 * @param {any} params 请求参数
 * @param {url} url 请求地址
 * @param {function} callback 回调函数
 */
function _requestPost(params, url, callback) {
    params['token'] = getUserToken();
    $.ajax({
        type: "POST",
        headers: {
            'token': getUserToken()
        },
        url: url,
        dataType: "json",
        async: true,
        contentType: "application/json",
        data: JSON.stringify(params),//将对象转为json字符串
        success: function (results) {
            if (results.code == 1) {
                callback(results.content);
            } else {
                alert(results.msg);
            }
        },
        error: function (results) {
            alert("请求错误")
        }
    });
}

/**
 * POST请求返回MSG信息
 * @param {any} params 请求参数
 * @param {url} url 请求地址
 * @param {function} callback 回调函数
 */
function _submitPost(params, url, callback) {
    params['token'] = getUserToken();
    $.ajax({
        type: "POST",
        headers: {
            'token': getUserToken()
        },
        url: url,
        dataType: "json",
        async: true,
        contentType: "application/json",
        data: JSON.stringify(params),//将对象转为json字符串
        success: function (results) {
            callback(results);
        },
        error: function (results) {
            alert("请求错误")
        }
    });
}

/**
 * 加载班级任务回调函数
 * @param {function} callback 回调函数
 * @param {any} callbackParams 回调函数参数
 */
function findClazzAllTasksJson(callback, callbackParams) {
    _jsonPost({ "clazzId": getStudentClazzId(), "currentPage": 1, "pageSize": 30 }, getUrl() + "/task/findClazzTasksPageJson", callback, callbackParams);
}
/**
 *  加载学院任务回调函数
 * @param {function} callback 回调函数
 * @param {any} callbackParams 回调函数参数
 */
function findAcademyAllTasksJson(callback, callbackParams) {
    _jsonPost({ "academyId": getStudentAcademyId(), "currentPage": 1, "pageSize": 30 }, getUrl() + "/task/findAcademyTasksPageJson", callback, callbackParams);
}

/**
 * 任务已完成头像
 * @param {function} callback 回调函数
 * @param {any} callbackParams 回调函数参数
 */
function loadTaskCompleteHead(callback, callbackParams) {
    _jsonPost({ "taskId": callbackParams, "currentPage": 1, "pageSize": 5 }, getUrl() + "/task/getTaskCompleteRecordPageJson", callback, callbackParams);
}


/**
 *  分页加载班级任务回调函数
 * @param {int} currentPage 当前页
 * @param {int} pageSize 页大小
 * @param {function} fun 回调函数
 */
function requestClazzTaskListPage(currentPage, pageSize, fun) {
    _requestPost({ "clazzId": getStudentClazzId(), "currentPage": currentPage, "pageSize": pageSize }, getUrl() + "/task/findClazzTasksPageJson", fun);
}


/**
 *  分页加载学院任务
 * @param {int} currentPage 当前页
 * @param {int} pageSize 页大小
 * @param {function} fun 回调函数
 */
function requestAcademyTaskListPage(currentPage, pageSize, fun) {
    _requestPost({ "academyId": getStudentAcademyId(), "currentPage": currentPage, "pageSize": pageSize }, getUrl() + "/task/findAcademyTasksPageJson", fun);
}

/**
 * 加载班级任务
 * @param {function} fun 回调函数
 */
function getClazzTaskListCount(fun) {
    _requestPost({ "clazzId": getStudentClazzId() }, getUrl() + "/task/getClazzTaskCount", fun);
}

/**
 * 加载学院任务
 * @param {function} fun 回调函数
 */
function getAcademyTaskListCount(fun) {
    _requestPost({ "academyId": getStudentAcademyId() }, getUrl() + "/task/getAcademyTaskCount", fun);
}

/**
 * 加载班级人数
 * @param {function} fun 回调函数
 */
function getClazzUserCountJson(fun) {
    _requestPost({ "clazzId": getStudentClazzId() }, getUrl() + "/clazz/getClazzUserCountJson", fun);
}

/**
 * 加载班级已完成任务
 * @param {function} fun 回调函数
 */
function getClazzCompleteTaskCountJson(fun) {
    _requestPost({ "clazzId": getStudentClazzId() }, getUrl() + "/task/getClazzCompleteTaskCountJson", fun);
}
/**
 * 加载班级已完成任务
 * @param {function} fun 回调函数
 */
function getClazzStartingTaskCountJson(fun) {
    _requestPost({ "clazzId": getStudentClazzId() }, getUrl() + "/task/getClazzStartingTaskCountJson", fun);
}