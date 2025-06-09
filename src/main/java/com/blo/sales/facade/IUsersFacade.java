package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blo.sales.facade.dto.DtoUser;
import com.blo.sales.facade.dto.DtoUserToken;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;

@RequestMapping("/api/v1/users")
public interface IUsersFacade {
	
	@PostMapping("/actions/login")
	ResponseEntity<DtoCommonWrapper<DtoUserToken>> login(@RequestBody DtoUser user);
	
	@PostMapping("/actions/register/root")
	ResponseEntity<DtoCommonWrapper<DtoUserToken>> registerRootUser(@RequestBody DtoUser rootUser);
	
	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("/mgmt/actions/register")
	ResponseEntity<DtoCommonWrapper<DtoUserToken>> registerUser(@RequestBody DtoUser user);
	
	@PreAuthorize("hasRole('ROOT')")
	@PutMapping("/mgmt/actions/reset/{username}")
	ResponseEntity<DtoCommonWrapper<DtoUser>> createTemporaryPassword(@RequestBody DtoUser rootDataUser, @PathVariable String username);
	
	@PutMapping("/actions/change-password")
	ResponseEntity<DtoCommonWrapper<DtoUser>> changePassword(@RequestBody DtoUser userData);

}
