/**
 * 加载个人设置
 */
function loadProfile() {
  iniLeftMenu();
  let state = { title: '', url: window.location.href.split("?")[0] };
  history.pushState(state, '', window.location.href.split("?")[0] + '?url=profile');
  document.getElementById("left_menu_myself_tree").classList.add("menu-open");
  document.getElementById("left_menu_myself").classList.add("active");
  document.getElementById("left_menu_profile").classList.add("active");
  $("#main_content").load("../share/profile.html");
}