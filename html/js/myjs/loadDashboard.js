/**
 * 加载班级仪表盘
 */
function loadClazzDashboard() {
    iniLeftMenu();
    let state = { title: '', url: window.location.href.split("?")[0] };
    history.pushState(state, '', window.location.href.split("?")[0] + '?url=clazzDashboard');
    document.getElementById("left_menu_clazz_dashboard_tree").classList.add("menu-open");
    document.getElementById("left_menu_dashboard").classList.add("active");
    document.getElementById("left_menu_clazz_dashboard").classList.add("active");
    $('#main_content').load("../share/clazzdashboard.html", function () {
        getClazzTaskListCount(function (count) {
            setElementValue('clazz_dashboard_taskCount', count);
        });

        getClazzUserCountJson(function (count) {
            setElementValue('clazz_dashboard_userCount', count);
        });

        getClazzCompleteTaskCountJson(function (count) {
            setElementValue('clazz_dashboard_taskEndCount', count);
        });

        getClazzStartingTaskCountJson(function (count) {
            setElementValue('clazz_dashboard_taskStartCount', count);
        });
    });
}