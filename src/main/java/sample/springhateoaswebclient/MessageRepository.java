package sample.springhateoaswebclient;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Rob Winch
 */
@RepositoryRestResource
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
}
