package pw.elka.pik.mkdev1.web.rest.dto;

import pw.elka.pik.mkdev1.domain.Board;
//import pw.elka.pik.mkdev1.domain.Task;
import pw.elka.pik.mkdev1.domain.TaskList;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardDTO {

	@NotNull
	private Long id;
	
	@NotNull
    @Size(min = 1, max = 50)
    private String name;
    
    private Set<TaskListDTO> taskLists;

	public BoardDTO(Board board) {
		this(board.getName(), board.getId(), 
				board.getTaskLists().stream().map(TaskListDTO::new).collect(Collectors.toSet()));}
		
	public BoardDTO(String name, Long id, Set<TaskListDTO> taskLists){
		this.name = name;
		this.id = id;
        this.taskLists = taskLists;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TaskListDTO> getTaskLists() {
		return taskLists;
	}

	public void setTaskLists(Set<TaskListDTO> taskLists) {
		this.taskLists = taskLists;
	}

    @Override
	public String toString() {
		return "BoardDTO{" +
	            "name='" + this.name + '\''  +
	            "id='" + this.id.toString() + '\''  +
            "}";
	}
}
