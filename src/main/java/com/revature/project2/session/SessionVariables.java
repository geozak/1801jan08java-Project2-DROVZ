package com.revature.project2.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.ScopedProxyMode;

import com.revature.project2.model.Trainer;
import com.revature.project2.repository.TrainerRepository;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope("session")
//@ScopedProxy
public class SessionVariables {
	
	@Autowired
	private TrainerRepository trainerRepository;

	private Integer trainerId;
	
	public SessionVariables() {
	}

	public Trainer getTrainer() {
		return trainerId == null ? null : trainerRepository.findOne(trainerId);
	}

	public void setTrainer(Trainer trainer) {
		this.setTrainerId(trainer == null ? null : trainer.getId());
	}

//	private Integer getTrainerId() {
//		return trainerId;
//	}

	private void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}
	

}
