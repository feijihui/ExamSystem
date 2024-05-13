new Vue({
  el: '#app',
  data() {
    return {
      papers: [], // 所有试卷信息
      searchText: '' // 搜索框中的文本
    };
  },
  computed: {
    // 根据搜索文本过滤试卷信息
    filteredPapers() {
      return this.papers.filter(paper => paper.bankName.includes(this.searchText));
    }
  },
  // created() {
  //   // 在页面加载时从服务器获取试卷信息
  //   this.fetchPapers();
  // },
  methods: {
    fetchPapers() {
	  // 将搜索库名提交
	  // const formData = new FormData();
	  // formData.append("questionBankName" , this.searchText);
      // 发送请求到服务器获取试卷信息
      // 假设您的服务器接口为 http://localhost:8080/getPapers
      // 替换为实际的接口地址
      fetch('http://localhost:27915/question/' + this.searchText)
        .then(response => response.json())
        .then(data => {
          // 将获取的试卷信息赋值给 papers 数组
          this.papers = data;
		  console.log('响应数据',data)
        })
        .catch(error => {
          console.error('获取试卷信息失败:', error);
        });
    },
    handleSearch() {
      // 当搜索框输入内容发生变化时触发搜索逻辑
      // 可以在这里发送搜索请求到服务器，或者在前端进行数据过滤
	  this.fetchPapers()
    },
	// 编辑操作
	// 在表格的每一行中，row 对象包含了当前行的所有数据。通过 {row}，您可以访问当前行的各个属性，
	updateQuestion(row) {
		console.log("点击了编辑")
		
	},
	
	// 删除操作
	// 在表格的每一行中，row 对象包含了当前行的所有数据。通过 {row}，您可以访问当前行的各个属性，
	deleteQuestion(row) {
		const formData = new FormData()
		formData.append("questionBankName" , this.searchText)
		console.log("点击了删除" + row.questionId)
		// 将试题id提交到服务器给删除试题
		fetch('http://localhost:27915/question/' + this.searchText + '/' + row.questionId , {
			method: 'DELETE'
		})
			.then(Response => {
				if (Response.ok){
					console.log("删除成功")
					this.fetchPapers()
				}
			})
	}
  }
});