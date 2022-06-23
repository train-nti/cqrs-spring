package id.go.beacukai.training.api.command;

import id.go.beacukai.training.model.Actor;
import id.go.beacukai.training.service.ActorCommandService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actorc")
public class ActorCController {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ActorCommandService actorCommandService;

    private static final Logger logger =
            LoggerFactory.getLogger(ActorCController.class);

    @PostMapping("/")
    public Actor tambah(@RequestBody Actor newActor){
        logger.debug(newActor.toString());
        return actorCommandService.add(newActor);
    }

}
