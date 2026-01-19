// 요소 잡기
var likeBtn = document.getElementById("likeBtn");
var ctxInput = document.getElementById("ctx");

// 이벤트 등록
if (likeBtn) {
  likeBtn.addEventListener("click", onLikeBtnClick);
}

function onLikeBtnClick() {
  var rNum = likeBtn.getAttribute("data-rnum");
  var ctx = getContextPath();
  var url = ctx + "/recipe/like/toggle";
  toggleLikeRequest(url, rNum);
}

function getContextPath() {
  if (!ctxInput) return "";
  return ctxInput.value || "";
}

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

function handleLikeJson(data) {
  console.log("LIKE RESULT:", data);
}

function printHttpError(text) {
  console.log("HTTP ERROR:", text);
}

function onLikeError(err) {
  console.log("FETCH ERROR:", err);
}
