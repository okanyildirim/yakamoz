package com.hof.yakamozauth.help.content.controller.ftpconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ftp")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FtpConfigController {

	private final FtpConfigService ftpConfigService;

	@PostMapping("/")
	public ApiResponse<Void> createFtpConfig(@RequestBody FtpConfigRequest request){
		request.validate();
		ftpConfigService.createFtpConfig(request);
		return ApiResponse.empty();
	}

	@GetMapping("/")
	public ApiResponse<List<FtpConfigResponse>> getFtpConfigs(){
		List<FtpConfigResponse> ftpConfigList = ftpConfigService.getFtpConfigs();
		return ApiResponse.of(ftpConfigList);
	}

	@GetMapping("/{id}")
	public ApiResponse<FtpConfigResponse> getFtpConfig(@PathVariable Long id){

		return ApiResponse.of(ftpConfigService.getFtpConfig(id));
	}

	@PutMapping("/{id}")
	public ApiResponse<Void> updateFtpConfig(@PathVariable Long id, @RequestBody FtpConfigRequest request){
		ftpConfigService.updateFtpConfig(id,request);
		return ApiResponse.empty();
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id){
		ftpConfigService.delete(id);
		return ApiResponse.empty();
	}

	@PostMapping("/test")
	public ApiResponse<Void> testFtpConnection(@RequestBody FtpConfigRequest ftpInfo){
		ftpConfigService.test(ftpInfo);
		return ApiResponse.empty();
	}
}
