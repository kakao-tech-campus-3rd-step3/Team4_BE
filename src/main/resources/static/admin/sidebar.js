function loadSidebar() {
  fetch('/admin/sidebar.html')
    .then(res => res.text())
    .then(html => {
      const container = document.getElementById('sidebar-container');
      container.innerHTML = html;

      document.getElementById("link-dashboard").href = `${BASE_URL}/admin/dashboard`;
      document.getElementById("link-products").href = `${BASE_URL}/admin/items`;
      document.getElementById("link-missions").href = `${BASE_URL}/admin/promotions`;
      document.getElementById("link-stats").href = `${BASE_URL}/admin/missions/stats/view`;
    })
    .catch(err => console.error("사이드바 로드 실패:", err));
}