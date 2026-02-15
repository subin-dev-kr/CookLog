// ===============================
// DOM 요소 잡기
// ===============================
var likeBtn = document.getElementById("likeBtn");
var ctxInput = document.getElementById("ctx");

// ===============================
// 이벤트 등록
// ===============================
document.addEventListener("DOMContentLoaded", initRecipeLike);

function initRecipeLike() {
  if (!likeBtn) return;
  likeBtn.addEventListener("click", onLikeBtnClick);
}

// ===============================
// 이벤트 핸들러
// ===============================
function onLikeBtnClick() {
  var rNum = likeBtn.getAttribute("data-rnum");
  if (!rNum) {
    console.log("❌ rNum 없음");
    return;
  }

  var ctx = getContextPath();
  var url = ctx + "/recipe/like/toggle";

  toggleLikeRequest(url, rNum);
}

// ===============================
// 유틸
// ===============================
function getContextPath() {
  if (!ctxInput) return "";
  return ctxInput.value || "";
}

// ===============================
// 서버 호출
// ===============================
function toggleLikeRequest(url, rNum) {
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
      "Accept": "application/json"
    },
    body: "rNum=" + encodeURIComponent(rNum)
  })
    .then(onLikeResponse)
    .catch(onLikeError);
}

function onLikeResponse(res) {
  if (!res.ok) {
    res.text().then(printHttpError);
    return;
  }
  res.json().then(handleLikeJson);
}

// ===============================
// 응답 처리 + UI 반영
// ===============================
function handleLikeJson(data) {
  console.log("LIKE RESULT:", data);
  applyLikeUI(data);
}

function applyLikeUI(data) {
  var icon = likeBtn.querySelector(".like-icon");
  if (!icon) return; // (아이콘이 없으면 그냥 종료)

  var liked = data.liked === true;

  if (liked) {
    icon.classList.remove("fa-regular");
    icon.classList.add("fa-solid", "liked");
  } else {
    icon.classList.remove("fa-solid", "liked");
    icon.classList.add("fa-regular");
  }
}

function printHttpError(text) {
  console.log("HTTP ERROR:", text);
}

function onLikeError(err) {
  console.log("FETCH ERROR:", err);
}
