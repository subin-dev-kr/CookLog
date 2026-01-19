// ===============================
// 1. DOM 요소 잡기
// ===============================
const tabLabels = document.querySelectorAll(".tab-button label");

// ===============================
// 2. 이벤트 등록
// ===============================
function initRecipeTabEvents() {
    tabLabels.forEach(function (label) {
        label.addEventListener("click", onTabClick);
    });
}

// ===============================
// 3. 이벤트 핸들러 (이름 있는 함수)
// ===============================
function onTabClick(event) {
    const clickedLabel = event.currentTarget;
    const sortType = clickedLabel.dataset.sort;

    if (!sortType) {
        console.error("❌ sortType이 정의되지 않았습니다:", clickedLabel);
        return;
    }

    moveToSortedPage(sortType);
}

// ===============================
// 4. 실제 동작 함수
// ===============================
function moveToSortedPage(sortType) {
    const params = new URLSearchParams(window.location.search);

    params.set("sort", sortType);
    params.set("page", 1);

    location.href = contextPath + "/?" + params.toString();
}

// ===============================
// 5. 초기 실행
// ===============================
document.addEventListener("DOMContentLoaded", function () {
    initRecipeTabEvents();
});
