console.log("[memberUpdateForm.js] loaded");

// 닉네임 / 이메일 중복확인 결과 저장용
let isNickOk = true;     // 수정페이지라 처음엔 true
let isEmailOk = true;
let isPwMatched = true; 

let lastCheckedNick = "";
let lastCheckedEmail = "";

document.addEventListener("DOMContentLoaded", initMemberUpdateForm);

function initMemberUpdateForm() {
  console.log("initMemberUpdateForm 실행됨");

  // 이벤트 전부 연결
  initDupState();
  bindDeleteEvent();
  bindPasswordEvents();
  bindNickEmailEvents();
  bindSubmitEvent();
}

function initDupState() {
  const nickInput = document.getElementById("mNickName");
  const emailInput = document.getElementById("email");

  // 수정폼은 기본값이 이미 있으니까 "현재 값"을 확인된 값으로 저장
  if (nickInput) lastCheckedNick = nickInput.value.trim();
  if (emailInput) lastCheckedEmail = emailInput.value.trim();
}

/* =========================
   탈퇴 버튼 이벤트
========================= */
function bindDeleteEvent() {
  const deleteBtn = document.getElementById("deleteBtn");
  console.log("deleteBtn =", deleteBtn);

  if (deleteBtn) {
    deleteBtn.addEventListener("click", confirmDelete);
  }
}

/* =========================
   비밀번호 이벤트
========================= */
function bindPasswordEvents() {
  const pwCheckBtn = document.getElementById("pwCheckBtn");
  const mPwInput = document.getElementById("mPw");
  const mPwConfirmInput = document.getElementById("mPwConfirm");

  if (pwCheckBtn) pwCheckBtn.addEventListener("click", pwCheck);
  if (mPwInput) mPwInput.addEventListener("input", resetPwCheck);
  if (mPwConfirmInput) mPwConfirmInput.addEventListener("input", resetPwCheck);
}

/* =========================
   닉네임/이메일 이벤트
========================= */
function bindNickEmailEvents() {
  const nickInput = document.getElementById("mNickName");
  const emailInput = document.getElementById("email");

  if (nickInput) {
    nickInput.addEventListener("blur", checkNickUpdate);
    nickInput.addEventListener("input", resetNickCheck);
  }

  if (emailInput) {
    emailInput.addEventListener("blur", checkEmailUpdate);
    emailInput.addEventListener("input", resetEmailCheck);
  }
}

/* =========================
   submit 이벤트
========================= */
function bindSubmitEvent() {
  const updateForm = document.getElementById("updateForm");
  if (updateForm) updateForm.addEventListener("submit", validateUpdateSubmit);
}
/* 비밀번호 재확인 */
function pwCheck() {
  const pw = document.getElementById("mPw").value;
  const pwConfirm = document.getElementById("mPwConfirm").value;
  const pwMsg = document.getElementById("pwCheckMsg");

  // 둘 다 비어 있음 → 변경 안 함 → 통과
  if (!pw && !pwConfirm) {
    setMsg(pwMsg, "", "");
    isPwMatched = true;
    hideFormAlert();
    return;
  }

  // 하나만 입력 → 실패
  if (!pw || !pwConfirm) {
    setMsg(pwMsg, "비밀번호와 재확인을 모두 입력하세요.", "red");
    isPwMatched = false;
    showFormAlert("비밀번호 재확인을 완료해주세요.", "danger");
    return;
  }

  // 둘 다 입력 → 비교
  if (pw === pwConfirm) {
    setMsg(pwMsg, "비밀번호가 일치합니다.", "green");
    isPwMatched = true;
    hideFormAlert();
  } else {
    setMsg(pwMsg, "비밀번호가 일치하지 않습니다.", "red");
    isPwMatched = false;
  }
}

function resetPwCheck() {
  const pw = document.getElementById("mPw").value;
  const pwConfirm = document.getElementById("mPwConfirm").value;
  const pwMsg = document.getElementById("pwCheckMsg");

  // 둘 다 비어 있음 → 변경 안 함 → 통과
  if (!pw && !pwConfirm) {
    setMsg(pwMsg, "", "");
    isPwMatched = true;
    return;
  }

  // 하나라도 입력되면 → 재확인 필요
  isPwMatched = false;
  setMsg(pwMsg, "비밀번호 재확인을 해주세요.", "gray");
}

/* 닉네임 중복확인 */
function checkNickUpdate() {
  const nickInput = document.getElementById("mNickName");
  const nickMsg = document.getElementById("nickMsg");

  const nick = nickInput.value.trim();

  // 비어 있으면
  if (!nick) {
    nickMsg.innerText = "닉네임을 입력하세요.";
    nickMsg.style.color = "red";
    isNickOk = false;
    lastCheckedNick = "";
    return;
  }

  // 서버에 물어보기
  fetch(`checkNickUpdate?nick=${encodeURIComponent(nick)}`)
    .then(res => res.text())
    .then(result => {
      const r = result.trim();

      if (r === "OK") {
        nickMsg.innerText = "사용 가능한 닉네임입니다.";
        nickMsg.style.color = "green";
        isNickOk = true;
        lastCheckedNick = nick;
      } else if (r === "DUP") {
        nickMsg.innerText = "이미 사용 중인 닉네임입니다.";
        nickMsg.style.color = "red";
        isNickOk = false;
        lastCheckedNick = "";
      }
    });
}

function resetNickCheck() {
  const nickInput = document.getElementById("mNickName");
  const nickMsg = document.getElementById("nickMsg");

  const cur = nickInput.value.trim();

  if (cur !== lastCheckedNick) {
    isNickOk = false;

    if (!cur) {
      nickMsg.innerText = "";
      return;
    }

    nickMsg.innerText = "닉네임 중복 확인이 필요합니다. (포커스 이동 시 자동 확인)";
    nickMsg.style.color = "gray";
  }
}

/* 이메일 중복확인 */
function checkEmailUpdate() {
  const emailInput = document.getElementById("email");
  const emailMsg = document.getElementById("emailMsg");

  const email = emailInput.value.trim();

  if (!email) {
    emailMsg.innerText = "이메일을 입력하세요.";
    emailMsg.style.color = "red";
    isEmailOk = false;
    lastCheckedEmail = "";
    return;
  }

  fetch(`checkEmailUpdate?email=${encodeURIComponent(email)}`)
    .then(res => res.text())
    .then(result => {
      const r = result.trim();

      if (r === "OK") {
        emailMsg.innerText = "사용 가능한 이메일입니다.";
        emailMsg.style.color = "green";
        isEmailOk = true;
        lastCheckedEmail = email;
      } else if (r === "DUP") {
        emailMsg.innerText = "이미 사용 중인 이메일입니다.";
        emailMsg.style.color = "red";
        isEmailOk = false;
        lastCheckedEmail = "";
      }
    });
}

function resetEmailCheck() {
  const emailInput = document.getElementById("email");
  const emailMsg = document.getElementById("emailMsg");

  const cur = emailInput.value.trim();

  if (cur !== lastCheckedEmail) {
    isEmailOk = false;

    if (!cur) {
      emailMsg.innerText = "";
      return;
    }

    emailMsg.innerText = "이메일 중복 확인이 필요합니다. (포커스 이동 시 자동 확인)";
    emailMsg.style.color = "gray";
  }
}

/* 중복확인 검증 */
function validateUpdateSubmit(e) {
  const nickInput = document.getElementById("mNickName");
  const emailInput = document.getElementById("email");
  
  if (!isPwMatched) {
     e.preventDefault();
     showFormAlert("비밀번호 재확인을 완료해주세요.", "danger");
     document.getElementById("mPwConfirm").focus();
     return;
   }

  if (!isNickOk || nickInput.value.trim() !== lastCheckedNick) {
    e.preventDefault();
    alert("닉네임 중복 확인을 완료해주세요.");
    nickInput.focus();
    return;
  }

  if (!isEmailOk || emailInput.value.trim() !== lastCheckedEmail) {
    e.preventDefault();
    alert("이메일 중복 확인을 완료해주세요.");
    emailInput.focus();
    return;
  }
}

// 탈퇴하기
function confirmDelete() {
  const form = document.getElementById("deleteForm");
  if (!form) return;

  const count = Number(window.recipeCount || 0);
  let msg;

  if (count > 0) {
    msg =
      "등록된 레시피가 " + count + "개 있습니다.\n" +
      "탈퇴 시 레시피는 삭제되지 않습니다.\n\n" +
      "그래도 탈퇴하시겠습니까?";
  } else {
    msg = "정말로 탈퇴하시겠습니까?";
  }

  if (confirm(msg)) {
    form.submit();
  }
}

/**** 공용 함수들 *****/
function setMsg(el, text, color) {
  if (!el) return;
  el.innerText = text || "";
  if (color) el.style.color = color;
}

function showFormAlert(text, type) {
  const formAlert = document.getElementById("formAlert");
  if (!formAlert) return;

  formAlert.innerText = text;
  formAlert.classList.remove("d-none");
  formAlert.classList.remove("alert-danger", "alert-success", "alert-warning", "alert-info");
  formAlert.classList.add("alert-" + type);
}

function hideFormAlert() {
  const formAlert = document.getElementById("formAlert");
  if (!formAlert) return;

  formAlert.innerText = "";
  formAlert.classList.add("d-none");
}
