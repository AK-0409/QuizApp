package com.example.quizapp.controller;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions()
    {
            return questionService.getAllQuestions();
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question> >getQuestionByCategory(@PathVariable String category)
    {
        return questionService.getQuestionsByCategory(category);
    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
        return questionService.addQuestion(question);
    }
    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable Integer id)
    {
        questionService.deleteQuestion(id);
        return "Deleted";
    }
    @PutMapping("update")
    public String updateQuestion(@RequestBody Question question)
    {
        return  questionService.updateQuestion(question);

    }




}
