package com.example.quizapp.service;

import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;
import com.example.quizapp.model.questionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Created", HttpStatus.OK);

    }

    public ResponseEntity<List<questionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionFronDB=quiz.get().getQuestions();
        List<questionWrapper> questionToUsers=new ArrayList<>();
        for(Question q:questionFronDB)
        {
            questionWrapper qw=new questionWrapper(q.getId(),q.getQuestion_title(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionToUsers.add(qw);

        }
        return new ResponseEntity<>(questionToUsers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResponses(Integer id, List<Response> responses) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> question=quiz.get().getQuestions();
        int right=0;
        int i=0;
        for(Response response:responses)
        {
            if(response.getResponse().equals(question.get(i).getRight_answer())) {
                right++;
            }

            i++;

        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
