console.log("js시작");

// 초기화 함수 (모든 이벤트 리스너 연결 및 대표 이미지 미리보기 설정)
function initializeRecipeForm() {
    
    // 버튼 및 영역 잡기 (요리 순서 추가)
    const addButton = document.getElementById("addForm"); // 버튼은 id가addForm 인거를 버튼으로 준다
    
    if (addButton) {
        addButton.addEventListener("click", addNewStep); // click 이벤트를 누르면 addNewStep함수를 실행해라
    }
    
    // 대표 이미지 미리보기 요소 잡기
    const imageInput = document.getElementById("rCenterImageFile");
    const imagePreview = document.getElementById("rCenterImagePreview");
    const imagePlaceholder = document.getElementById("rCenterImagePlaceholder");

    // 대표 이미지 이벤트 연결
    if (imageInput) {
        // change 이벤트 발생 시 previewImage 함수 호출
        imageInput.addEventListener("change", function(event) {
            previewImage(event.target, imagePreview, imagePlaceholder);
        });
    }
	//초기 STEP 1 이미지 미리보기 연결
    const initialStepInput = document.querySelector("#stepArea .step-block .form-control[type='file']");
    const initialStepPreview = document.querySelector("#stepArea .step-block .stepImagePreview");
    const initialStepPlaceholder = document.querySelector("#stepArea .step-block .stepImagePlaceholder");
    
    if (initialStepInput) {
        // 새로 추가된 .step-file-input 클래스를 초기 요소에 부여
        initialStepInput.classList.add('step-file-input'); 
        
        initialStepInput.addEventListener("change", function(event) {
            previewImage(event.target, initialStepPreview, initialStepPlaceholder);
        });
    }
}

// 이미지 미리보기 로직 함수
function previewImage(input, previewElement, placeholderElement) {
    
    if (input.files && input.files[0]) {
        
        const reader = new FileReader();

        reader.onload = function(e) {
            previewElement.src = e.target.result;
            // 미리보기 이미지를 보이게 하고 플레이스홀더 숨김
            previewElement.style.display = 'block';
            if (placeholderElement) {
                 placeholderElement.classList.add("d-none");
            }
        };

        reader.readAsDataURL(input.files[0]);
        
    } else {
        // 파일 선택 취소 시 초기화
        previewElement.src = '#';
        previewElement.style.display = 'none';
        if (placeholderElement) {
            placeholderElement.classList.remove("d-none");
        }
    }
}

// 요리 순서 이미지 미리보기 처리 함수
function handleStepImagePreview(input) {
    // 1. 클릭된 파일 인풋의 부모 STEP 블록을 찾습니다.
    const stepBlock = input.closest('.step-block');
    // 2. 그 STEP 블록 안의 미리보기 영역을 찾습니다.
    const previewArea = stepBlock.querySelector('.image-preview-container');

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


// 전역변수
const stepArea = document.getElementById("stepArea"); 

//함수정의
function addNewStep() { //요리순서 추가
	console.log("클릭함");
	const index = stepArea.querySelectorAll(".step-block").length; // 클래스 step-block 의 개수를 세어 인덱스 (순서)결정
	const block = document.createElement("div"); //createElement는 추가할 태그("div")를 생성하겠다
	block.className = "step-block card p-4 shadow-sm mb-3 border"; // 그 블럭의 클래스이름이 step-block이다
	// 그 블럭안에있는 html 요소가 h3 인 블럭을 추가할때 1씩 번호를 증가시킨다
	block.innerHTML = `
			<div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="fs-6 fw-bold m-0">STEP ${index + 1}</h4>
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
				        
				        <div class="image-preview-container mb-3 border rounded w-100 image-hidden">
				            <img class="stepImagePreview image-cover" data-step-id="0" src="#" alt="단계 이미지 미리보기"> 
				            <span class="stepImagePlaceholder">이미지 미리보기</span>
				        </div>
				        
				        <input class="form-control stepImageFile" type="file" id="cookingSteps[${index}].cImageFile" 
				        	name="cookingSteps[${index}].cImageFile" accept="image/*" onchange="handleStepImagePreview(this)">
				        <input type="hidden" name="cookingSteps[${index}].cImage" value=""/>
				    </div>
				</div>
				
            </div>
	    `;

	stepArea.appendChild(block); //부모 요소 stepArea 안에 자식요소 block 를 추가하겠다
	// 새로 추가된 블록의 파일 입력 필드에 이벤트 리스너 연결
    const newFileInput = block.querySelector(".step-file-input");
    const newImagePreview = block.querySelector(".stepImagePreview");
    const newImagePlaceholder = block.querySelector(".stepImagePlaceholder");
    
    if (newFileInput) {
        newFileInput.addEventListener("change", function(event) {
            previewImage(event.target, newImagePreview, newImagePlaceholder);
        });
    }
}

// 요리순서 삭제 함수
function removeStepField(btn) { // 요리순서 삭제
	const blocks = stepArea.querySelectorAll(".step-block");

	if (blocks.length <= 1) {
		alert("최소 1단계는 있어야 합니다..");
		return;
	}
	
	if (!confirm('정말로 삭제하시겠습니까?')) { // 삭제 버튼 누르면 메세지 실행
		return;
	}

	// 클릭된 블록 삭제
	const target = btn.closest('.step-block');
	target.remove();
	
	// 재정렬 함수호출
	reorderSteps();
}
	//재정렬
	function reorderSteps(){
		
	let newBlocks = stepArea.querySelectorAll(".step-block");
	console.dir(newBlocks);
	
	for(let i = 0; i < newBlocks.length; i++){
		
		let block = newBlocks[i];
		// Step 번호 제설정
		block.querySelector("h4").innerText = "STEP " + (i + 1);
		// 요리내용 재설정
		const contentTextarea = block.querySelector("textarea[name*='cInstructions']");
		if(contentTextarea) {
			contentTextarea.name = 'cookingSteps[' + i + '].cInstructions';
		}
		// 이미지 파일 재설정
		const imageFileInput = block.querySelector("input[type='file'][name*='cImageFile']");
		if(imageFileInput){
			imageFileInput.name = 'cookingSteps[' + i + '].cImageFile';
		}
		//hieeen 필드
		// cImage (저장될 파일 경로/이름)
        const cImageHidden = block.querySelector("input[type='hidden'][name*='cImage']");
        if (cImageHidden) {
             cImageHidden.name = 'cookingSteps[' + i + '].cImage';
        }
        
        // cNum (데이터베이스 고유 번호. 필수가 아닐 수 있음)
        const cNumHidden = block.querySelector("input[type='hidden'][name*='cNum']");
        if (cNumHidden) {
             cNumHidden.name = 'cookingSteps[' + i + '].cNum';
        }
        
        // cStep (단계 순서 번호)
        // cStep은 필수 필드이므로 존재 여부를 확인하고 업데이트
        const cStepHidden = block.querySelector("input[type='hidden'][name*='cStep']");
        if (cStepHidden) {
             cStepHidden.name = 'cookingSteps[' + i + '].cStep';
             cStepHidden.value = i; // 0부터 시작하는 인덱스 값으로 변경
		}
	}
}
document.addEventListener("DOMContentLoaded", initializeRecipeForm);