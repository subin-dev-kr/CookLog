console.log("search tab js loaded");

// -----------------------------
// 1. 요소 잡기
// -----------------------------
const tabTitle = document.getElementById("tab-title");       // 요리명 라디오 버튼
const tabNick = document.getElementById("tab-nickname"); 	 // 닉네임 라디오 버튼

const inputTitle = document.getElementById("input-title");   //요리명 검색창
const inputNick = document.getElementById("input-nickName"); // 닉네임 검색창

// -----------------------------
// 2. 이벤트 주기
// -----------------------------
tabTitle.addEventListener("change", applySearchName);
tabNick.addEventListener("change", applySearchName);

// -----------------------------
// 3. 함수 선언 + 정의
// -----------------------------
function applySearchName() {

    inputTitle.removeAttribute("name");
    inputNick.removeAttribute("name");

    if (tabTitle.checked) {
        inputTitle.setAttribute("name", "searchKeyword");
    }

    if (tabNick.checked) {
        inputNick.setAttribute("name", "searchKeyword");
    }
}

// -----------------------------
// 4. 처음 로딩될 때 기본 상태 설정
// -----------------------------
document.addEventListener("DOMContentLoaded", applySearchName);