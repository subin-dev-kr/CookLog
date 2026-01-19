
// 추가버튼 선택
const addButton = document.getElementById("addform");
// addButton변수에 클릭이벤트 시 addNewStep함수 호출하기
addButton.addEventListener("click", addNewStep);addButton

function addNewStep() {
	
    // 현재 step-block의 개수를 세어 새로운 인덱스(순서)를 결정
    const index = stepArea.querySelectorAll(".step-block").length; 
    
    // 새로운 div 요소를 생성하고 클래스 부여
    const block = document.createElement("div"); 
    block.className = "step-block"; 
    
    // UI 요소의 HTML 구조 정의
    // ⭐️ name 속성: Spring이 List로 바인딩할 수 있도록 cookingSteps[index] 형태로 작성
    block.innerHTML = `
        <h3>Step ${index + 1}</h3>
        <textarea name="cookingSteps[${index}].cInstructions" rows="4" cols="60" required></textarea><br> 
        <input type="file" name="cookingSteps[${index}].cImageFile"/><br>
        <input type="hidden" name="cookingSteps[${index}].cImage" value="" />
        <input type="hidden" name="cookingSteps[${index}].cNum" value="0" />
        <input type="hidden" name="cookingSteps[${index}].cStep" value="${index + 1}" />
        
        <button type="button" onclick="removeStepField(this)">삭제</button>
    `;

    // stepArea 컨테이너의 마지막에 생성된 블록을 추가
    stepArea.appendChild(block);
}
/*<h3>Step ${index + 1}</h3>
	        <textarea name="cookingSteps[${index}].cInstructions" rows="4" cols="60" required></textarea><br> 
	        <input type="file" name="cookingSteps[${index}].cImageFile"/><br>
			
			<input type="hidden" name="cookingSteps[${index}].cImage" value=""/><br>
			<input type="hidden" name="cookingSteps[${index}].cNum" value="0" />
			<input type="hidden" name="cookingSteps[${index}].cStep" value="0">
			<button type="button" class="add_step_btn">순서추가</button>
	        <button type="button" onclick="removeStepField(this)">삭제</button>*/