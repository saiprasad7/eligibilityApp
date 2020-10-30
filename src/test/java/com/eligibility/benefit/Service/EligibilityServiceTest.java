package com.eligibility.benefit.Service;
import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
/*import org.springframework.test.util.AssertionErrors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;*/

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EligibilityServiceTest {

    public static final String SUBSCRIBER_ID = "1234";
    public static final String policy_id = "12345";
    @InjectMocks
    private EligibilityService eligibilityService;
    @Mock
    private SubscriberRepository subscriberRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenSubscriberIsPresentAndEligibleShouldReturnEligibilityInfo() {
        Users users = users();
        Mockito.when(userRepository.findByToken(SUBSCRIBER_ID)).thenReturn(users);
        Subscribers subscribers = subscribers();
        Mockito.when(subscriberRepository.findBySubscriberId(SUBSCRIBER_ID)).thenReturn(subscribers);

        Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, "00", SUBSCRIBER_ID, SUBSCRIBER_ID);

        Assertions.assertThat(check).isInstanceOf(EligibilityCheck.class);
        EligibilityCheck converted = (EligibilityCheck) check;

        Assertions.assertThat(converted.getPlanCode()).isEqualTo("1234");
        Assertions.assertThat(converted.getCurrentEligibleAmount()).isEqualTo(100);
        Assertions.assertThat(converted.getSubscriberId()).isEqualTo(SUBSCRIBER_ID);
    }
    

    @Test
    void whenGetEligibilityCalledAndDependentsExistAndIsEligibleShouldReturnTrue() {
        Mockito.when(subscriberRepository.findBySubscriberId(SUBSCRIBER_ID)).thenReturn(subscribers());
        Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, SUBSCRIBER_ID, SUBSCRIBER_ID, SUBSCRIBER_ID);
        Assertions.assertThat(check).isInstanceOf(EligibilityCheck.class);
        EligibilityCheck converted = (EligibilityCheck) check;

        Assertions.assertThat(converted.getSubscriberId()).isEqualTo(SUBSCRIBER_ID);
        
        Assertions.assertThat(converted.getCurrentEligibleAmount()).isEqualTo(100L);
        Assertions.assertThat(converted.getPlanCode()).isNotNull();
        Assertions.assertThat(converted.getRelationShip()).isNotEmpty();
        Assertions.assertThat(converted.getRelationShip().get(0).getDependentId()).isEqualTo(SUBSCRIBER_ID);
    }

    @Test
    void whenGetEligibilityCalledAndPlanNotExistAndIsEligibleShouldReturnFalse() {
        Mockito.when(subscriberRepository.findBySubscriberId(policy_id)).thenReturn(subscribersNoEligible());
        Object check = eligibilityService.getEligibility(policy_id, policy_id, policy_id, policy_id);
        Assertions.assertThat(check.toString().contains("errorCode"));
    
    }
  
    @Test
    void whenGetEligibilityCalledAndDependentsValNotExistAndIsEligibleShouldReturnFalse() {
        Mockito.when(subscriberRepository.findBySubscriberId(SUBSCRIBER_ID)).thenReturn(subscribers());
        Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, policy_id, SUBSCRIBER_ID, SUBSCRIBER_ID);
        Assertions.assertThat(check.toString().contains("errorCode"));
    }
    
    @Test
    void whenGetEligibilityCalledAndNoDependentAndIsEligibleShouldReturnFalse() {
        Mockito.when(subscriberRepository.findBySubscriberId(policy_id)).thenReturn(subscribersNoEligible());
        Object check = eligibilityService.getEligibility(policy_id, policy_id, policy_id, SUBSCRIBER_ID);
        Assertions.assertThat(check.toString().contains("errorCode"));
    
    }
    
     @Test
    void whenGetEligibilityCalledAndEmptySubscriberOrPlantExistAndIsEligibleShouldReturnFalse() {
        Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, SUBSCRIBER_ID, null, SUBSCRIBER_ID);
        Assertions.assertThat(check.toString().contains("errorCode"));
     }
   
    @Test
    void whenGetEligibilityCalledAndSubscriberisnotNulltExistAndIsEligibleShouldReturnFalse() {
        Mockito.when(subscriberRepository.findBySubscriberId(policy_id)).thenReturn(subscribersNoEligible());
        Object check = eligibilityService.getEligibility(policy_id, policy_id, policy_id, SUBSCRIBER_ID);
        Assertions.assertThat(check.toString().contains("errorCode"));
      
   
    }

    @Test
    void whenGetEligibilityCalledAndDependentsNotExistAndIsEligibleShouldReturnFalse() {
        Mockito.when(subscriberRepository.findBySubscriberId(SUBSCRIBER_ID)).thenReturn(subscribers());
        Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, null, SUBSCRIBER_ID, SUBSCRIBER_ID);
        Assertions.assertThat(check).isInstanceOf(EligibilityCheck.class);
        EligibilityCheck converted = (EligibilityCheck) check;

        Assertions.assertThat(converted.getSubscriberId()).isEqualTo(SUBSCRIBER_ID);
        Assertions.assertThat(converted.getPlanCode()).isNotNull();
        Assertions.assertThat(converted.getUniqueId()).isNull();
    }
    @Test
    void whenGetEligibilityCalledAndSubscriberNotinDBExistAndIsEligibleShouldReturnFalse() {
    	
  Object check = eligibilityService.getEligibility("11111", SUBSCRIBER_ID, SUBSCRIBER_ID, SUBSCRIBER_ID);
  Assertions.assertThat(check.toString().contains("errorCode"));
    }
    
  @Test
  void whenGetEligibilityCalledAndSubscriberOfEmptyDependentAndIsEligibleShouldReturnFalse() {
	  Mockito.when(subscriberRepository.findBySubscriberId(SUBSCRIBER_ID)).thenReturn(subscribersOfDependentEmpty());
Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, SUBSCRIBER_ID, SUBSCRIBER_ID, SUBSCRIBER_ID);
Assertions.assertThat(check.toString().contains("errorCode"));
  }
  //subscribersOfDependentEmpty
    
    @Test
    void whenGetEligibilityCalledAndDependentsNotMatchedAndIsEligibleShouldReturnFalse() {
        Mockito.when(subscriberRepository.findBySubscriberId(SUBSCRIBER_ID)).thenReturn(subscriberswithoutEligibile());
        Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, SUBSCRIBER_ID, SUBSCRIBER_ID, SUBSCRIBER_ID);
        Assertions.assertThat(check).isInstanceOf(EligibilityCheck.class);
        EligibilityCheck converted = (EligibilityCheck) check;

        Assertions.assertThat(converted.getSubscriberId()).isEqualTo(SUBSCRIBER_ID);
        
        Assertions.assertThat(converted.getCurrentEligibleAmount()).isEqualTo(0L);
        Assertions.assertThat(converted
        		.getPlanCode()).isNotNull();
        Assertions.assertThat(converted.getRelationShip()).isNotEmpty();
        Assertions.assertThat(converted.getRelationShip().get(0).getDependentId()).isEqualTo(SUBSCRIBER_ID);
    }
    

    private Subscribers subscribers() {
        Subscribers subscribers = new Subscribers();
        subscribers.setSubscriberId(SUBSCRIBER_ID);
        subscribers.setBenefits(benefits());
        subscribers.setDependents(Collections.singletonList(getDependent("father", SUBSCRIBER_ID)));
        subscribers.setId(SUBSCRIBER_ID);
        Name name = new Name();
        name.setFirstName("first");
        name.setLastName("last");
        subscribers.setName(name);
        return subscribers;
    }

    private Subscribers subscriberswithoutEligibile() {
        Subscribers subscribers = new Subscribers();
        subscribers.setSubscriberId(SUBSCRIBER_ID);
        subscribers.setBenefits(benefits2());
        subscribers.setDependents(Collections.singletonList(getDependentVal("father", SUBSCRIBER_ID)));
        subscribers.setId(SUBSCRIBER_ID);
        Name name = new Name();
        name.setFirstName("first");
        name.setLastName("last");
        subscribers.setName(name);
        return subscribers;
    }
    private Dependents getDependent(String relation, String id) {
        Dependents dependents = new Dependents();
        dependents.setDependentBenefits(benefits());
        dependents.setDependentId(id);
        Name name = new Name();
        name.setLastName("last " + id);
        name.setFirstName("first " + id);
        dependents.setDependentName(name);
        dependents.setDependentRelation(relation);
        return dependents;
    }

    private Dependents getDependentVal(String relation, String id) {
        Dependents dependents = new Dependents();
        dependents.setDependentBenefits(benefits2());
        dependents.setDependentId(id);
        Name name = new Name();
        name.setLastName("last " + id);
        name.setFirstName("first " + id);
        dependents.setDependentName(name);
        dependents.setDependentRelation(relation);
        return dependents;
    }
    private Users users() {
        Users users = new Users();
        users.setId("1234");
        users.setEmail("email@email.com");
        users.setPassword("1234");
        users.setName("Name");
        users.setToken("");
        return users;
    }

    private List<Benefit> benefits() {
        List<Benefit> benefits = new ArrayList<>();

        Benefit benefit = getBenefit();

        benefits.add(benefit);
        return benefits;
    }

    private Benefit getBenefit() {
        Benefit benefit = new Benefit();
        benefit.setId(SUBSCRIBER_ID);
        benefit.setClaimedAmount(0);
        benefit.setPolicyId(SUBSCRIBER_ID);
        benefit.setCurrentEligibleAmount(100);
        benefit.setPolicyName("Name");
        return benefit;
    }

    private Subscribers subscribersNoEligible() {
        Subscribers subscribers = new Subscribers();
        subscribers.setSubscriberId(policy_id);
        subscribers.setBenefits(benefits1());
        subscribers.setDependents(Collections.singletonList(getDependent("", policy_id)));
        subscribers.setId(policy_id);
        Name name = new Name();
        name.setFirstName("first");
        name.setLastName("last");
        subscribers.setName(name);
        return subscribers;
    }
    private Subscribers subscribersOfDependentEmpty() {
        Subscribers subscribers = new Subscribers();
        subscribers.setSubscriberId(policy_id);
        subscribers.setBenefits(benefits());
        subscribers.setDependents(Collections.singletonList(getDependent("", "")));
        subscribers.setId(policy_id);
        Name name = new Name();
        name.setFirstName("first");
        name.setLastName("last");
        subscribers.setName(name);
        return subscribers;
    }
       
    private List<Benefit> benefits1() {
        List<Benefit> benefits = new ArrayList<>();

        Benefit benefit = getBenefit1();

        benefits.add(benefit);
        return benefits;
    }
    private List<Benefit> benefits2() {
        List<Benefit> benefits = new ArrayList<>();

        Benefit benefit = getBenefitwithoutEligble();

        benefits.add(benefit);
        return benefits;
    }
    
    
    private Benefit getBenefit1() {
        Benefit benefit1 = new Benefit();
        benefit1.setId(policy_id);
        benefit1.setClaimedAmount(0);
        benefit1.setPolicyId(policy_id);
        benefit1.setCurrentEligibleAmount(0);
        benefit1.setPolicyName("Name");
        return benefit1;
    }

    private Benefit getBenefitwithoutEligble() {
        Benefit benefit = new Benefit();
        benefit.setId(SUBSCRIBER_ID);
        benefit.setClaimedAmount(0);
        benefit.setPolicyId(SUBSCRIBER_ID);
        benefit.setCurrentEligibleAmount(0);
        benefit.setPolicyName("Name");
        return benefit;
    }
}
