package id.go.beacukai.training.api.query;

import id.go.beacukai.training.model.Actor;
import id.go.beacukai.training.service.ActorQueryService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/actorq")
public class ActorQController {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ActorQueryService actorQueryService;


    private static final Logger logger =
            LoggerFactory.getLogger(ActorQController.class);

    @GetMapping("/index")
    public List<Actor> index(){
        return actorQueryService.getAll();
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam String keyword){
        logger.debug("Find actor = " + keyword);
        return actorQueryService.searchRedis(keyword);
    }

    @GetMapping("/")
    public String greet(){

        logger.debug("Debug Message Logged !!!");
        return "hello";
    }
}
