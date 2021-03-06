package pw.elka.pik.mkdev1.service;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pw.elka.pik.mkdev1.domain.Board;
import pw.elka.pik.mkdev1.domain.Project;
import pw.elka.pik.mkdev1.domain.Task;
import pw.elka.pik.mkdev1.domain.TaskList;
import pw.elka.pik.mkdev1.repository.TaskListRepository;
import pw.elka.pik.mkdev1.repository.TaskRepository;
import pw.elka.pik.mkdev1.web.rest.dto.ProjectDTO;
import pw.elka.pik.mkdev1.web.rest.dto.TaskDTO;
import pw.elka.pik.mkdev1.web.rest.dto.TaskListDTO;

import javax.inject.Inject;
import java.util.Optional;

@Service
@Transactional
public class TaskService {
	
    private final Logger log = LoggerFactory.getLogger(BoardService.class);
    @Inject
    private TaskRepository taskRepository;
    @Inject
    private TaskListRepository taskListRepository;
    
	public void create(TaskDTO taskDTO) {
		taskListRepository.findOneById(taskDTO.getTaskListId()).ifPresent(list -> {
			final Task task = new Task();
			task.setName(taskDTO.getName());
			taskRepository.save(task);
			list.getTasks().add(task);
			taskListRepository.save(list);
		});
	}
	
	public void update(TaskDTO taskDTO) {
		taskRepository.findOneById(taskDTO.getId()).ifPresent(task -> {
			task.setName(taskDTO.getName());
			final long oldListId = task.getTaskList().getId();
			final long newListId = taskDTO.getTaskListId();
			if(oldListId != newListId) {
				taskListRepository.findOneById(oldListId).ifPresent(list -> list.getTasks().remove(task));
				taskListRepository.findOneById(newListId).ifPresent(list -> list.getTasks().add(task));
			}
		});
	}

	public boolean exists(Long id) {
		return taskRepository.findOneById(id).isPresent();
	}

    @Transactional(readOnly = true)
    public Optional<TaskDTO> getById(Long id) {
        return taskRepository.findOneById(id).map(TaskDTO::new);
    }
}
