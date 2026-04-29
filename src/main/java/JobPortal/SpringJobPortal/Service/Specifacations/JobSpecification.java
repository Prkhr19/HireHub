package JobPortal.SpringJobPortal.Service.Specifacations;

import JobPortal.SpringJobPortal.Entity.Job;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import JobPortal.SpringJobPortal.Entity.type.Jobtype;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Job>hasStatus(JobStatus status){

        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status));
    }

    public static Specification<Job>hasLocation(String location){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
               criteriaBuilder.lower( root.get("location")), "%" + location.toLowerCase()+ "%");

    }

    public static Specification<Job>hasKeyword(String keyword){
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")) , "%" + keyword.toLowerCase() + "%"),
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")) , "%" + keyword.toLowerCase() + "%")
        );



    }
    public static Specification<Job>hasJobType(Jobtype jobtype){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("jobType"), jobtype);
    }

    public static Specification<Job>hasCompany(String company){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("company").get("name")), "%" + company.toLowerCase() + "%");
    }

}
