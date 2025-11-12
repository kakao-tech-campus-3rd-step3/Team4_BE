function loadSidebar() {
  fetch('/admin/sidebar.html')
  .then(res => res.text())
  .then(html => {
    const container = document.getElementById('sidebar-container');
    container.innerHTML = html;

    document.getElementById("link-dashboard").href = `/admin/dashboard`;
    document.getElementById("link-products").href = `/admin/items`;
    document.getElementById("link-missions").href = `/admin/promotions`;
    document.getElementById("link-stats").href = `/admin/missions/stats/view`;
  })
  .catch(err => console.error("사이드바 로드 실패:", err));
}
