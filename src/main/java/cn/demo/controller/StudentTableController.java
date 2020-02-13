package cn.demo.controller;


import cn.demo.entity.StudentTable;
import cn.demo.service.IStudentTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus autoGenerator
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/student")
public class StudentTableController {
    @Autowired
    private IStudentTableService studentTableService;
    /**
     * 保存学生
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Boolean save(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "age", required = false) Integer age) {
        StudentTable studentTable = new StudentTable();
        studentTable.setStudentName(name);
        studentTable.setStudentAge(age);
        return studentTableService.save(studentTable);
    }

    /**
     * 查询学生
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public StudentTable query(@RequestParam(value = "id", required = false) Integer id) {
        return studentTableService.getById(id);
    }

    /**
     * 修改学生
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Boolean update(@RequestParam(value = "id", required = false) Integer id,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "age", required = false) Integer age) {
        StudentTable studentTable = new StudentTable();
        studentTable.setId(id);
        studentTable.setStudentName(name);
        studentTable.setStudentAge(age);
        return studentTableService.updateById(studentTable);
    }

    /**
     * 删除学生
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public Boolean remove(@RequestParam(value = "id", required = false) Integer id) {
        return studentTableService.removeById(id);
    }
}
