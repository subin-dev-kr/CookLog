// 전역 요소 정의
const stepArea = document.getElementById("stepArea");

// ==========================================================
// 1. 초기화 함수 (모든 이벤트 리스너 연결 및 대표 이미지 미리보기 설정)
// ==========================================================
function initializeRecipeForm() {
    
    // 요리 순서 추가 버튼 이벤트 연결 (DOM이 로드된 후에 안전하게 실행)
    const addButton = document.getElementById("addForm"); 
    if (addButton) {
        addButton.addEventListener("click", addNewStep);
    }
    
    // 대표 이미지 미리보기 초기화 및 이벤트 연결
    const imageInput = document.getElementById("rCenterImageFile");
    const imagePreview = document.getElementById("rCenterImagePreview");
    const imagePlaceholder = document.getElementById("rCenterImagePlaceholder");

    if (imageInput) {
        // change 이벤트 발생 시 previewImage 함수 호출
        imageInput.addEventListener("change", function(event) {
            previewImage(event.target, imagePreview, imagePlaceholder);
        });
    }
}

// ==========================================================
// 2.이미지 미리보기 로직 함수
// ==========================================================
function previewImage(input, previewElement, placeholderElement) {
    
    if (input.files && input.files[0]) {
        
        const reader = new FileReader();

        reader.onload = function(e) {
            previewElement.src = e.target.result;
            previewElement.style.display = 'block';
            if (placeholderElement) {
                placeholderElement.style.display = 'none';
            }
        };

        reader.readAsDataURL(input.files[0]);
        
    } else {
        // 파일 입력이 완전히 비었을 경우에만 초기 상태로 되돌림
        if (input.value === "") {
             previewElement.src = '#';
             previewElement.style.display = 'none';
             if (placeholderElement) {
                placeholderElement.style.display = 'block';
             }
        }
    }
}

// 요리 순서 이미지 미리보기 처리 함수
function handleStepImagePreview(input) {
    // 1. 클릭된 파일 인풋의 부모 STEP 블록을 찾습니다.
    const stepBlock = input.closest('.step-block');
    // 2. 그 STEP 블록 안의 미리보기 영역을 찾습니다.
    const previewArea = stepBlock.querySelector('.step-image-preview-area');

    if (input.files && input.files[0]) {
        const file = input.files[0];
        const reader = new FileReader();

        reader.onload = function(e) {
			// 숨김 클래스 제거해서 실제로 보이도록 처리
			previewArea.classList.remove("image-hidden");
            // 3. 미리보기 영역 내부를 비우고 새 <img> 태그를 삽입합니다.
            previewArea.innerHTML = `<img src="${e.target.result}" alt="새 스텝 이미지" class="stepImagePreview image-cover d-block">`;
        };

        reader.readAsDataURL(file);
    } else {
        // 파일 선택을 취소했을 경우 (기존 이미지 경로 유지)
        // 기존 이미지가 있다면 기존 이미지를 보여주고, 없다면 비워둡니다.
        // 현재 JSP 구조상 기존 이미지는 JSTL에 의해 이미 <c:if>로 출력되어 있으므로, 
        // 여기서는 파일이 없으면 미리보기 영역을 초기화합니다.
        previewArea.innerHTML = ''; 
    }
}

// ==========================================================
// 3. 요리 순서 추가 함수
// ==========================================================
function addNewStep() {
    console.log("요리 순서 추가 클릭");
    const index = stepArea.querySelectorAll(".step-block").length;
    const block = document.createElement("div");
    
    // 🌟 JSP의 클래스와 일치하도록 수정 (스타일 복구) 🌟
    block.className = "step-block card p-4 shadow-sm mb-3 border"; 

	block.innerHTML = `
		<div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="fs-6 fw-bold m-0">STEP ${index + 1}</h4>
            <input type="hidden" name="cookingSteps[${index}].cNum" value="0"> 
            <input type="hidden" name="cookingSteps[${index}].cStep" value="${index}">
            <button type="button" class="btn btn-sm btn-danger" onclick="removeStepField(this)">STEP 삭제</button> 
        </div>
        
        <div class="row g-3">
            <div class="col-lg-7">
                <label for="cookingSteps[${index}].cInstructions" class="form-label small text-muted">요리 내용</label>
                <textarea class="form-control" id="cookingSteps[${index}].cInstructions" name="cookingSteps[${index}].cInstructions"  rows="5" placeholder="이 단계의 요리 순서를 자세히 적어주세요." required></textarea>
            </div>
            
            <div class="col-lg-5">
                <div class="card bg-light p-3 h-100 d-flex flex-column justify-content-center">
                    <label for="cookingSteps[${index}].cImageFile" class="form-label small text-muted">요리 이미지</label>
                    <div class="step-image-preview-area mb-3 border rounded w-100 image-hidden"></div> 
                    
                    <input class="form-control" type="file" id="cookingSteps[${index}].cImageFile" name="cookingSteps[${index}].cImageFile" accept="image/*" onchange="handleStepImagePreview(this)">
					<input type="hidden" name="cookingSteps[${index}].cImage" value=""/>
                </div>
            </div>
        </div>
	`;

    stepArea.appendChild(block);
}

// ==========================================================
// 4. 요리 순서 삭제/재정렬 로직
// ==========================================================

function removeStepField(btn) {
    const blocks = stepArea.querySelectorAll(".step-block");

    if (blocks.length <= 1) {
        alert("최소 1단계는 있어야 합니다.");
        return;
    }

    if (!confirm("정말로 삭제하시겠습니까?")) {
        return;
    }

    const target = btn.closest(".step-block");
	
    target.remove();

    reorderSteps();
}

function reorderSteps() {

    const newBlocks = stepArea.querySelectorAll(".step-block");

    for (let i = 0; i < newBlocks.length; i++) {
		
        const block = newBlocks[i];
        
        // 1. Step 번호 재설정
        block.querySelector("h4").innerText = "STEP " + (i + 1); // "Step" -> "STEP "으로 JSP와 일치

        // 2. 필드 이름 재설정
        
        const contentTextarea = block.querySelector("textarea[name*='cInstructions']");
        if (contentTextarea) {
            contentTextarea.name = 'cookingSteps[' + i + '].cInstructions';
        }

        const imageFileInput = block.querySelector("input[type='file'][name*='cImageFile']");
        if (imageFileInput) {
            imageFileInput.name = 'cookingSteps[' + i + '].cImageFile';
        }
        
        const cStepHidden = block.querySelector("input[type='hidden'][name*='cStep']");
        if (cStepHidden) {
             cStepHidden.name = 'cookingSteps[' + i + '].cStep';
             cStepHidden.value = i; 
        }

        const cImageHidden = block.querySelector("input[type='hidden'][name*='cImage']");
        // cImageFile과 충돌 방지
        if (cImageHidden && !cImageHidden.name.includes('cImageFile')) {
             cImageHidden.name = 'cookingSteps[' + i + '].cImage';
        }
        
        const cNumHidden = block.querySelector("input[type='hidden'][name*='cNum']");
        if (cNumHidden) {
             cNumHidden.name = 'cookingSteps[' + i + '].cNum';
        }
    }
}
// 5. 초기화 함수 연결
document.addEventListener("DOMContentLoaded", initializeRecipeForm);