package edu.jxau.vhr.controller.statistics;

import edu.jxau.vhr.annotation.OpLog;
import edu.jxau.vhr.model.Attendance;
import edu.jxau.vhr.model.RespBean;
import edu.jxau.vhr.model.RespPageBean;
import edu.jxau.vhr.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/attendance")
public class StaAttendController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/")
    @OpLog("打卡")
    public RespBean addClockRecord(@RequestBody Attendance attendance) {
        System.out.println(attendance.getUsername());
        System.out.println(attendance.getClickTime());
        if (attendanceService.addClockRecord(attendance) == 1) {
            return RespBean.ok("打卡成功!");
        }
        return RespBean.error("打卡失败!");
    }

    @OpLog("分页获取员工考勤信息")
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Attendance attendance, @RequestParam(defaultValue = "") String name) {
        return attendanceService.getAttendanceByPage(page, size, attendance, name);
    }
}
