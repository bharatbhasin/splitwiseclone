package com.bbh.splitwise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbh.splitwise.model.Group;
import com.bbh.splitwise.model.User;
import com.bbh.splitwise.repository.GroupRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Group", description = "Endpoints for managing groups")
public class GroupController {

	@Autowired
	GroupRepository groupRepo;

	@RequestMapping(method = RequestMethod.POST, value = "/group")
	@Operation(summary = "Create a new group", description = "Create a new group.", tags = { "Group" }, responses = {
			@ApiResponse(description = "Success", responseCode = "201", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Group.class)) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Group> createGroup(@RequestBody Group group) {
		try {
			if (group != null) {
				return new ResponseEntity<Group>(HttpStatus.BAD_REQUEST);
			} else {
				groupRepo.save(group);
				return new ResponseEntity<Group>(group, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<Group>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{groupId}")
	@Operation(summary = "Get group with id", description = "Get group with id.", tags = { "Group" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Group.class)) }),
			@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Group> getGroupById(@PathVariable("groupId") Long id) {
		try {
			Optional<Group> groupInDB = groupRepo.findById(id);
			if (groupInDB.isPresent()) {
				Group group = groupInDB.get();
				return new ResponseEntity<Group>(group, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{groupId}/users")
	@Operation(summary = "Get all users in the group with id", description = "Get all users in the group with id.", tags = {
			"Group" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<User>> getUsers(@PathVariable("groupId") Long id) {
		try {
			Optional<Group> groupInDB = groupRepo.findById(id);
			if (groupInDB.isPresent()) {
				Group group = groupInDB.get();
				List<User> users = group.getUsers();
				return new ResponseEntity<>(users, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception exception) {
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{groupId}")
	@Operation(summary = "Update the group with groupId", description = "Update the group with groupId.", tags = {
			"Group" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = Group.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Group> updateGroup(@PathVariable("groupId") Long id, @RequestBody Group group) {
		if (group == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			try {
				Optional<Group> groupInDBOpt = groupRepo.findById(id);
				if (groupInDBOpt.isPresent()) {
					Group groupInDb = groupInDBOpt.get();
					groupInDb.setName(group.getName());
					groupInDb.setUsers(group.getUsers());
					groupInDb.setExpenses(group.getExpenses());
					return new ResponseEntity<Group>(group, HttpStatus.OK);
				} else {
					return new ResponseEntity<Group>(HttpStatus.OK);
				}
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{groupId}/user")
	@Operation(summary = "Add user to the group with groupId", description = "Add user to the group with groupId.", tags = {
			"Group" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = Group.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Group> addUser(@PathVariable("groupId") Long id, @RequestBody User user) {
		Group group = new Group();
		return new ResponseEntity<Group>(group, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{groupId}")
	@Operation(summary = "Delete the group with groupId", description = "Delete the group with groupId.", tags = {
			"Group" }, responses = {
					@ApiResponse(description = "Success", responseCode = "204", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = Group.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Group> deleteGroup(@PathVariable("groupId") Long id) {
		Group group = new Group();
		return new ResponseEntity<Group>(group, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{groupId}/user/{userId}")
	@Operation(summary = "Delete user with userId from the group with groupId", description = "Delete user with userId from the group with groupId.", tags = {
			"Group" }, responses = {
					@ApiResponse(description = "Success", responseCode = "204", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = Group.class)) }),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content) })
	public ResponseEntity<Group> deleteUser(@PathVariable("groupId") Long id, @PathVariable("userId") Long userId) {
		Group group = new Group();
		return new ResponseEntity<Group>(group, HttpStatus.NO_CONTENT);
	}

}
