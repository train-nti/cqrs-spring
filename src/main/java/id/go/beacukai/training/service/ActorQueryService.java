package id.go.beacukai.training.service;

import id.go.beacukai.training.model.Actor;
import id.go.beacukai.training.repository.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActorQueryService {
    private static final Logger logger =
            LoggerFactory.getLogger(ActorQueryService.class);
    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<Actor> getAll(){
        Iterable<Actor> actors = actorRepository.findAll();
        List<Actor> actorList = new ArrayList<>();

        actors.forEach(actor -> actorList.add(actor));
        return actorList;
    }

    public List<Actor> search(String keyword){
        List<Actor> actorsFirstName = actorRepository.findByFirstNameContainingIgnoreCase(keyword);
        List<Actor> actorsLastName = actorRepository.findByLastNameContainingIgnoreCase(keyword);

        logger.debug("first name found = " + actorsFirstName.size());
        actorsFirstName.forEach(actor -> logger.debug(actor.getFirstName()));
        logger.debug("last name found = " + actorsLastName.size());
        actorsLastName.forEach(actor -> logger.debug(actor.getLastName()));

        Set<Actor> actorSet = new LinkedHashSet<>(actorsFirstName);
        actorSet.addAll(actorsLastName);

        return new ArrayList<>(actorSet);
        // Stream.concat(actorsFirstName.stream(), actorsFirstName.stream())
        // .collect(Collectors.toList());
    }


    public List<String> searchRedis(String prefix){

        return new ArrayList<>(
                redisTemplate.opsForSet().members("name_" + prefix)
        );
    }

}
