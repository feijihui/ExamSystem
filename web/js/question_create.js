new Vue({
  el: '#app',
  data() {
    return {
      question: {
        bankName: '',
        questionText: '',
        questionType: '单选',
        questionAnswer: '',
        questionSelectType: '',
        questionPointValue: 0,
        questionProblem: 0,
        questionAnalyze: ''
      },
      questions: [],
      showQuestions: []
    };
  },
  methods: {
    addQuestion() {
      // 构造表单数据
      const formData = new FormData();
      formData.append('bankName', this.question.bankName);
      formData.append('questionText', this.question.questionText);
      formData.append('questionType', this.question.questionType);
      formData.append('questionAnswer', this.question.questionAnswer);
      formData.append('questionSelectType', this.question.questionSelectType);
      formData.append('questionPointValue', this.question.questionPointValue);
      formData.append('questionProblem', this.question.questionProblem);
      formData.append('questionAnalyze', this.question.questionAnalyze);

      // 发送 POST 请求到服务器
      fetch('http://localhost:27915/question/add', {
        method: 'POST',
        body: formData
      })
      .then(response => {
        if (response.ok) {
          console.log('成功提交题目');
          // 清空表单数据
		  this.questions.push({ ...this.question });
          this.$refs.questionForm.resetFields();
		  // 添加 "questions" 到 showQuestions 数组，展开题库题目列表
		  this.showQuestions.push('questions');
        } else {
          console.error('提交题目失败:', response.status);
        }
      })
      .catch(error => {
        console.error('提交题目失败:', error);
      });
    }
  }
});