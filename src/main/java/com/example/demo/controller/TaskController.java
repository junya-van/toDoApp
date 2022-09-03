package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * タスクリスト画面表示
	 */
	@GetMapping("/list")
	public String task(TaskForm form, Model model, @AuthenticationPrincipal User user) throws Exception{
		
		// Springセキュリティで認証済みのユーザ情報を取得
		String loginUserId = user.getUsername();
		
		if(loginUserId == null) {
			
			throw new Exception();
			
		}
		
		// 新規登録か更新かを判断する仕掛け(初回時はtrueをセット)
		form.setNewTask(true);
	
		List<Task> taskList = taskService.getAll(loginUserId);
		
		model.addAttribute("taskList", taskList);
		model.addAttribute("userId", loginUserId);
		
		return "task/taskList";
		
	}
	
	/**
	 * タスクを一件新規登録
	 */
	@PostMapping("/insert")
	public String insert(@Valid @ModelAttribute TaskForm form, BindingResult result, Model model, @AuthenticationPrincipal User user) {
		
		if(!result.hasErrors()) {
			// TaskFormをTaskに変換
			Task task = makeTask(form, 0, user.getUsername());
			
			// Taskを1件登録
			taskService.insert(task);
			
			// リダイレクト
			return "redirect:/task/list";
			
		}
		
		form.setNewTask(true);
		//model.addAttribute("taskForm", taskForm);
		List<Task> taskList = taskService.getAll(user.getUsername());
		model.addAttribute("taskList", taskList);
		model.addAttribute("userId", user.getUsername());
		return "task/taskList";
		
	}
	
	/**
	 * 1件のタスクデータを取得し、フォーム内に表示
	 */
	@GetMapping("/{task_id}")
	public String showUpdate(TaskForm form, @PathVariable int task_id, Model model, @AuthenticationPrincipal User user) {
		
		Optional<Task> taskOpt = taskService.getTask(task_id);
		Optional<TaskForm> taskFormOpt = taskOpt.map(t -> makeTaskForm(t));
		
		if(taskFormOpt.isPresent()) {
			form = taskFormOpt.get();
		}
		
		model.addAttribute("userId", user.getUsername());
		model.addAttribute("taskForm", form);
		List<Task> taskList = taskService.getAll(user.getUsername());
		model.addAttribute("taskList", taskList);
		model.addAttribute("taskId", task_id);
		
		return "task/taskList";
		
	}
	
	/**
	 * タスクIDを取得し、1件のデータを更新
	 * @param form
	 * @param result
	 * @param taskId
	 * @param model
	 * @param redirectAttribute
	 * @param user
	 * @return
	 */
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute TaskForm form,
			BindingResult result,
			@RequestParam("taskId") int taskId,
			Model model,
			RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal User user) {
		
		if(!result.hasErrors()) {
			
			Task task = makeTask(form, taskId, user.getUsername());
			
			// タスク更新処理
			taskService.update(task);
			
			// フラッシュスコープに成功メッセージセット
			redirectAttributes.addFlashAttribute("complete", "変更が完了しました");
			// リダイレクト
			return "redirect:/task/" + taskId;
			
		}
		
		model.addAttribute("userId", user.getUsername());
		model.addAttribute("taskForm", form);
		model.addAttribute("taskId", taskId);
		return "task/taskList";
		
	}
	
	/**
	 * タスクIDを取得し、タスクを1件削除する
	 * @param taskId
	 * @param model
	 * @return
	 */
	@PostMapping("/delete")
	public String delete(@RequestParam("taskId") int taskId, Model model) {
		
		taskService.deleteTask(taskId);
		return "redirect:/task/list";
		
	}
	
	/**
	 * 複製用に一件タスクデータを取得し、フォーム内に表示
	 * @param form
	 * @param taskId
	 * @param user
	 * @return
	 */
	@GetMapping("/duplicate")
	public String duplicate(TaskForm taskForm, @RequestParam("taskId") int taskId, Model model, @AuthenticationPrincipal User user) {
		
		Optional<Task> taskOpt = taskService.getTask(taskId);
		Optional<TaskForm> taskFormOpt = taskOpt.map(t -> makeTaskForm(t));
		if(taskFormOpt.isPresent()) {
			taskForm = taskFormOpt.get();
		}
		
		List<Task> taskList = taskService.getAll(user.getUsername());
		
		taskForm.setNewTask(true);
		
		model.addAttribute("userId", user.getUsername());
		model.addAttribute("taskForm", taskForm);
		model.addAttribute("taskList", taskList);
		model.addAttribute("taskId", taskId);
		
		return "task/taskList";
		
	}
	
	/**
	 * 指定したタスクタイプのタスク一覧を表示する
	 * @param taskForm
	 * @param typeId
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/selectType")
	public String selectType(TaskForm taskForm, @RequestParam("typeId") int typeId, Model model, @AuthenticationPrincipal User user) {
		
		taskForm.setNewTask(true);
		
		List<Task> taskList = taskService.getByTask(typeId, user.getUsername());
		
		model.addAttribute("taskList", taskList);
		model.addAttribute("userId", user.getUser_name());
		
		return "task/taskList";
		
	}
	
	/**
	 * TaskFormをTaskに変換
	 * @param form タスクフォームクラス
	 * @param taskId 新規登録の場合は0を指定
	 * @return Taskインスタンス
	 */
	private Task makeTask(TaskForm form, int taskId, String userId) {
		
		Task task = new Task();
		if(taskId != 0) {
			task.setTask_id(taskId);
		}
		
		task.setUser_id(userId);
		task.setType_id(form.getTypeId());
		task.setTitle(form.getTitle());
		task.setDetail(form.getDetail());
		task.setDeadline(form.getDeadline());
		
		return task;
		
	}
	
	/**
	 * TaskをTaskFormに変換
	 * @param task
	 * @return TaskFormインスタンス
	 */
	private TaskForm makeTaskForm(Task task) {
		
		TaskForm taskForm = new TaskForm();
		taskForm.setTypeId(task.getType_id());
		taskForm.setTitle(task.getTitle());
		taskForm.setDetail(task.getDetail());
		taskForm.setDeadline(task.getDeadline());
		taskForm.setNewTask(false);	// 編集モードの為falseをセットする
		
		return taskForm;
		
	}
	
}
