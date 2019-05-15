

from flask import  *
import MySQLdb
import os
from werkzeug.utils import secure_filename
app=Flask(__name__)
app.static_folder="static"
path="F:\\project\\new\\Third_Eye\\static\\employee_pic"
path1="F:\\project\\new\\Third_Eye\\static\\employee_work"
path3="F:\\project\\new\\Third_Eye\\static\\file"
path4="F:\\project\\new\\Third_Eye\\static\\report"
con=MySQLdb.connect(host='localhost',user='root',passwd='',port=3306,db='project')
cmd=con.cursor()


@app.route('/log',methods=['POST','GET'])
def log():
    username = request.args.get('username')
    password= request.args.get('password')
    print(username)
    print(password)
    cmd.execute("select * from login_tb where username='" + str(username) + "' and password='" + str(password) + "' AND type= 'TL'")
    s = cmd.fetchone()
    if (s is None):
        return jsonify({'task': "invalid"})
    else:
        return jsonify({'task': str(s[0])})



@app.route('/tl_view_employee',methods=['POST','GET'])
def tl_view_employee():
    try:
        lid = request.args.get('lid')
        cmd.execute("select * from h_add_tb where hid='"+str(lid)+"'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
       print(str(e))
       return jsonify({'result': "na"})

@app.route('/delete_profile',methods=['POST','GET'])
def delete_profile():
    lid = request.args.get('id')

    cmd.execute("delete from h_add_tb where eid=" + str(lid) + "")
    con.commit()
    return jsonify({'task': "deleted"})


@app.route('/tl_register',methods=['POST','GET'])
def tl_register():
    em_type=request.form['em_type']
    hid = request.form['headid']
    name = request.form['name']
    place = request.form['place']
    dob = request.form['dob']
    gender = request.form['gender']
    mobile = request.form['mobile']
    email =request.form['email']
    qualification = request.form['qualification']
    experience = request.form['experience']
    photo = request.files['files']
    img = secure_filename(photo.filename)
    photo.save(os.path.join(path, img))
    cmd.execute("insert into login_tb values(null,'" + email + "','" + mobile + "','employee')")
    tid=con.insert_id()
    print(tid)
    cmd.execute("insert into h_add_tb values('"+str(tid)+"','"+str(hid)+"','" + em_type + "','" + name + "','" + place + "','" + dob + "','" + gender + "','" + mobile + "','" + email + "','" + qualification + "','" + experience + "','" + img + "')")
    con.commit()
    return jsonify({'task': "success"})

@app.route('/tl_view_admin_work',methods=['POST','GET'])

def tl_view_admin_work():
    try:
        hid = request.args.get('hid')
        cmd.execute("select * from a_assign_work_tb where hid='" + str(hid) + "'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
        print(str(e))
        return jsonify({'result': "na"})


@app.route('/tl_view_assigned_work',methods=['POST','GET'])
def tl_view_assigned_work():
    try:
        hid = request.args.get('hid')
        cmd.execute("SELECT h_add_tb.name,h_assign_work_tb.* FROM h_add_tb JOIN h_assign_work_tb ON h_add_tb.eid=h_assign_work_tb.eid where h_assign_work_tb.hid='" + str(hid) + "'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
        print(str(e))
        return jsonify({'result': "na"})


@app.route('/tl_assign_work_emp', methods=['POST', 'GET'])
def tl_assign_work_emp():
        #wr_id = request.form['wr_id']
        eid= request.form['eid']

        title = request.form['title']
        files = request.files['files']
        fname=secure_filename(files.filename)
        files.save(os.path.join(path1,fname))
        date = request.form['sdate']
        hid = request.form['hid']
        print("insert into h_assign_work_tb values(null,'"+hid+"','"+eid+"','"+fname+"','"+title+"','"+date+"')")
        cmd.execute("insert into h_assign_work_tbs values(null,'"+hid+"','"+eid+"','"+fname+"','"+title+"','"+date+"')" )
        con.commit()
        return jsonify({'task': "success"})

@app.route('/fileuploadServlet', methods=['POST', 'GET'])
def fileuploadServlet():
    try:

        eid=request.form['eid']
        title = request.form['title']
        wid = request.form['wid']

        files = request.files['files']
        fname=secure_filename(files.filename)
        files.save(os.path.join(path1,fname))
        date = request.form['sdate']

        hid = request.form['hid']
        print("insert into h_assign_work_tb  values(null,'"+hid+"','"+eid+"','"+fname+"','"+title+"','"+date+"','"+wid+"')")
        cmd.execute("insert into h_assign_work_tb  values(null,'"+hid+"','"+eid+"','"+fname+"','"+title+"','"+date+"','"+wid+"')" )
        con.commit()
        return jsonify({'task': "success"})
    except Exception as e:
        print(e)
        return jsonify({'task': "failed"})











@app.route('/view_name',methods=['POST','GET'])
def view_name():
    try:
        lid = request.args.get('lid')
        cmd.execute("select eid,name from h_add_tb where hid='" + str(lid) + "'")

        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
        print(str(e))
        return jsonify({'result': "na"})

@app.route('/view_title', methods=['POST', 'GET'])
def view_title():
        try:
            lid = request.args.get('lid')
            cmd.execute("select wid,title from a_assign_work_tb where hid='" + str(lid) + "'")
            row_headers = [x[0] for x in cmd.description]
            emd = cmd.fetchall()
            if emd is not None:
                json_data = []
                for result in emd:
                    json_data.append(dict(zip(row_headers, result)))
                    print(json_data)
                return jsonify(json_data)
            else:
                return jsonify({"res": "no"})
        except Exception as e:
            print(str(e))
            return jsonify({'result': "na"})

@app.route('/view_report_head', methods=['GET', 'POST'])
def view_report_head():
            eid = request.args.get('eid')

            cmd.execute("SELECT `h_assign_work_tb`.`title`,`report_send_employee`.* FROM `h_assign_work_tb` JOIN `report_send_employee` ON `h_assign_work_tb`.`wr_id`=`report_send_employee`.`wr_id` WHERE `report_send_employee`.`eid`='"+str(eid)+"'")
            print("SELECT `h_assign_work_tb`.`title`,`report_send_employee`.* FROM `h_assign_work_tb` JOIN `report_send_employee` ON `h_assign_work_tb`.`wr_id`=`report_send_employee`.`wr_id` WHERE `report_send_employee`.`eid`='"+str(eid)+"'")
            row_headers = [x[0] for x in cmd.description]
            results = cmd.fetchall()
            json_data = []
            for result in results:
                json_data.append(dict(zip(row_headers, result)))
            con.commit()
            print(json_data)
            return jsonify(json_data)

@app.route('/view_title1', methods=['POST', 'GET'])
def view_title1():
        try:
            lid = request.args.get('lid')
            cmd.execute("select wr_id,title from h_assign_work_tb where hid='" + str(lid) + "'")
            row_headers = [x[0] for x in cmd.description]
            emd = cmd.fetchall()
            if emd is not None:
                json_data = []
                for result in emd:
                    json_data.append(dict(zip(row_headers, result)))
                    print(json_data)
                return jsonify(json_data)
            else:
                return jsonify({"res": "no"})
        except Exception as e:
            print(str(e))
            return jsonify({'result': "na"})



@app.route('/tl_view_pc_details',methods=['POST','GET'])
def tl_view_pc_details():
    try:
        lid= request.args.get('lid')
        #hid = request.args.get('hid')
        cmd.execute("SELECT * FROM system_reg WHERE hid='"+str(lid)+"'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
       print(str(e))
       return jsonify({'result': "na"})

@app.route('/tl_reg_system', methods=['POST', 'GET'])
def tl_reg_system():
        try:
            sys_num= request.args.get('sys_num')
            sys_adrs = request.args.get('sys_adrs')
            hid= request.args.get('hid')

            print("insert into system_reg values(null,'" +sys_num + "','" +sys_adrs + "')")
            cmd.execute("insert into system_reg values(null,'" +sys_num + "','" +sys_adrs + "','" +hid+ "')")
            con.commit()
            return jsonify({'result': "success"})
        except Exception as e:
            print(e)
            return jsonify({'result': "failed"})

@app.route('/fileuploadServlet1', methods=['POST', 'GET'])
def fileuploadServlet1():
            try:
                hid = request.args.get('hid')
                wid = request.args.get('wr_id')
                report = request.args.get('report')

                print(hid)
                cmd.execute("insert into send_report_tb values(null,'" + hid + "',curdate(),'" + report + "','" + wid + "')")

                con.commit()
                return jsonify({'task': "success"})
            except Exception as e:
                print("kkk")
                return jsonify({'task': e})


####office employee####

@app.route('/office_emp_login',methods=['GET','POST'])
def office_emp_login():
    uname=request.args.get('uname')
    passwrd=request.args.get('pass')
    cmd.execute("select * from login_tb where username='"+uname+"' and password='"+passwrd+"' and type='employee'")
    s=cmd.fetchone()
    print(s)
    if (s is None):
        return jsonify({'result':"failed"})
    else:
        return jsonify({'result':s[0]})

@app.route('/profile_view', methods=['POST', 'GET'])
def profile_view():
        try:
            lid = request.args.get('lid')
            cmd.execute("select * from h_add_tb where hid='" + str(lid) + "'")
            row_headers = [x[0] for x in cmd.description]
            emd = cmd.fetchall()
            if emd is not None:
                json_data = []
                for result in emd:
                    json_data.append(dict(zip(row_headers, result)))
                    print(json_data)
                return jsonify(json_data)
            else:
                return jsonify({"res": "no"})
        except Exception as e:
            print(str(e))
            return jsonify({'result': "na"})

@app.route('/view_work', methods=['POST', 'GET'])
def view_work():
            try:
                eid = request.args.get('id')
                cmd.execute("select * from h_assign_work_tb where eid='" + str(eid) + "'")
                row_headers = [x[0] for x in cmd.description]
                emd = cmd.fetchall()
                if emd is not None:
                    json_data = []
                    for result in emd:
                        json_data.append(dict(zip(row_headers, result)))
                        print(json_data)
                    return jsonify(json_data)
                else:
                    return jsonify({"res": "no"})
            except Exception as e:
                print(str(e))
                return jsonify({'result': "na"})

@app.route('/office_employee_send_report', methods=['POST', 'GET'])
def office_employee_send_report():
                try:

                    eid = request.args.get('emp_id')
                    # files = request.files['files']
                    wid = request.args.get('wid')
                    report=request.args.get('report')
                    import time
                    timestr = time.strftime("%Y%m%d-%H%M%S")+".txt"
                    f=open("F:/project/new/Third_Eye/static/report/"+timestr,"x")
                    f=open("F:/project/new/Third_Eye/static/report/"+timestr,"w")
                    f.write(report)

                    #
                    # fname = secure_filename(files.filename)
                    # files.save(os.path.join(path1, fname))
                    # hid = request.form['hid']
                    print("insert into report_send_employee values(null,'" + eid + "','" + wid + "',curdate(),'" + timestr + "')")
                    cmd.execute("insert into report_send_employee values(null,'" + eid + "','" + wid + "',curdate(),'" + timestr + "')")
                    con.commit()
                    return jsonify({'task': "success"})
                except Exception as e:
                    print(e)
                    return jsonify({'task': "failed"})


#  MARKETING HEAD WEB SERVICE START#


@app.route('/login_mh',methods=['POST','GET'])
def login_mh():
    username = request.args.get('username')
    password= request.args.get('password')
    print(username)
    print(password)
    cmd.execute("select * from login_tb where username='" + str(username) + "' and password='" + str(password) + "'and type='MH'")
    s = cmd.fetchone()
    if (s is None):
        return jsonify({'task': "invalid"})
    else:
        return jsonify({'task': str(s[0])})

@app.route('/mh_view_employee',methods=['POST','GET'])
def mh_view_employee():
    try:
        lid = request.args.get('lid')
        cmd.execute("select * from h_add_tb where hid='"+str(lid)+"'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
       print(str(e))
       return jsonify({'result': "na"})

@app.route('/mh_delete_profile', methods=['POST', 'GET'])
def mh_delete_profile():
        lid = request.args.get('id')

        cmd.execute("delete from h_add_tb where eid=" + str(lid) + "")
        con.commit()
        return jsonify({'task': "deleted"})

@app.route('/mh_register', methods=['POST', 'GET'])
def mh_register():
        em_type = request.form['em_type']
        hid = request.form['headid']
        name = request.form['name']
        place = request.form['place']
        dob = request.form['dob']
        gender = request.form['gender']
        mobile = request.form['mobile']
        email = request.form['email']
        qualification = request.form['qualification']
        experience = request.form['experience']
        photo = request.files['files']
        img = secure_filename(photo.filename)
        photo.save(os.path.join(path, img))
        cmd.execute("insert into login_tb values(null,'" + email + "','" + mobile + "','employee')")
        tid = con.insert_id()
        print(tid)
        cmd.execute("insert into h_add_tb values('"+str(tid)+"','" + str(hid) + "','" + em_type + "','" + name + "','" + place + "','" + dob + "','" + gender + "','" + mobile + "','" + email + "','" + qualification + "','" + experience + "','" + img + "')")
        con.commit()
        return jsonify({'task': "success"})


@app.route('/mh_view_admin_work',methods=['POST','GET'])
def mh_view_admin_work():
    try:
        hid = request.args.get('hid')
        cmd.execute("select * from a_assign_work_tb where hid='" + str(hid) + "'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
        print(str(e))
        return jsonify({'result': "na"})

@app.route('/mh_view_assigned_work', methods=['POST', 'GET'])
def mh_view_assigned_work():
        try:
            hid = request.args.get('hid')
            cmd.execute("select h_add_tb.name,mh_assign_work.* from h_add_tb JOIN mh_assign_work ON h_add_tb.eid=mh_assign_work.eid where mh_assign_work.hid='"+str(hid)+"'")
            row_headers = [x[0] for x in cmd.description]
            emd = cmd.fetchall()
            if emd is not None:
                json_data = []
                for result in emd:
                    json_data.append(dict(zip(row_headers, result)))
                    print(json_data)
                return jsonify(json_data)
            else:
                return jsonify({"res": "no"})
        except Exception as e:
            print(str(e))
            return jsonify({'result': "na"})

@app.route('/mh_work_assign', methods=['POST', 'GET'])
def mh_work_assign():
            try:

                eid = request.form['eid']
                location = request.form['location']
                files = request.files['files']
                title=request.form['title']
                date = request.form['sdate']
                wid = request.form['wid']
                fname = secure_filename(files.filename)
                files.save(os.path.join(path1, fname))

                hid = request.form['hid']
                print("insert into mh_assign_work  values(null,'" + eid + "','" + hid + "','"+title+"',''" + fname + "','" +location + "','" + date + "','"+wid+"')")
                cmd.execute("insert into mh_assign_work  values(null,'" + eid + "','" + hid + "','"+title+"','" + fname + "','" +location + "','" + date + "','"+wid+"')")
                con.commit()
                return jsonify({'task': "success"})
            except Exception as e:
                print(e)
                return jsonify({'task': "failed"})

@app.route('/mh_view_report', methods=['GET', 'POST'])
def mh_view_report():
                eid = request.args.get('eid')

                cmd.execute("SELECT `mh_assign_work`.`title`,`m_emp_send_report`.* FROM `mh_assign_work` JOIN `m_emp_send_report` ON `mh_assign_work`.`work_id`=`m_emp_send_report`.`work_id` WHERE `m_emp_send_report`.`eid`='" + eid + "'")

                row_headers = [x[0] for x in cmd.description]
                results = cmd.fetchall()
                print(results)
                json_data = []
                for result in results:
                    json_data.append(dict(zip(row_headers, result)))
                con.commit()
                print(json_data)
                return jsonify(json_data)


@app.route('/send_report', methods=['POST', 'GET'])
def send_report():
            try:
                hid = request.args.get('hid')
                wid = request.args.get('work_id')
                report = request.args.get('report')

                print(hid)
                cmd.execute("insert into `send_report_tb` values(null,'" + hid + "',curdate(),'" + report + "','" + wid + "')")

                con.commit()
                return jsonify({'task': "success"})
            except Exception as e:
                print("kkk")
                return jsonify({'task': e})




@app.route('/mh_view_phone_details', methods=['POST', 'GET'])
def mh_view_phone_details():
        try:
            hid= request.args.get('hid')
            # hid = request.args.get('hid')
            cmd.execute("select * from phone_reg where hid='"+hid+"'")
            row_headers = [x[0] for x in cmd.description]
            emd = cmd.fetchall()
            if emd is not None:
                json_data = []
                for result in emd:
                    json_data.append(dict(zip(row_headers, result)))
                    print(json_data)
                return jsonify(json_data)
            else:
                return jsonify({"res": "no"})
        except Exception as e:
            print(str(e))
            return jsonify({'result': "na"})

@app.route('/mh_reg_phone', methods=['POST', 'GET'])
def mh_reg_phone():
            try:
                imei= request.args.get('imei')
                model= request.args.get('model')
                hid = request.args.get('hid')
                os= request.args.get('os')
                ph_num= request.args.get('ph_num')
                print("insert into phone_reg values(null,'" + str(hid)+ "','" + imei + "','" + model+ "','" + os+ "','" + ph_num+ "')")
                cmd.execute("insert  into `phone_reg` values(null,'" + str(hid)+ "','" + imei + "','" + model+ "','" + os+ "','" + ph_num+ "')")
                con.commit()
                return jsonify({'result': "success"})
            except Exception as e:
                print(e)
                return jsonify({'result': "failed"})




#### MARKETING EMPLOYEEE DUTY######

@app.route('/EmployeeLogin',methods=['GET','POST'])
def EmployeeLogin():
    uname=request.args.get('uname')
    passwrd=request.args.get('pass')
    cmd.execute("select * from login_tb where username='"+uname+"' and password='"+passwrd+"' and type='employee'")
    s=cmd.fetchone()
    if (s is None):
        return jsonify({'result':"failed"})
    else:
        return jsonify({'result':s[0]})

@app.route('/view_emp_profile', methods=['POST', 'GET'])
def view_emp_profile():
        try:
            lid = request.args.get('lid')
            cmd.execute("select * from h_add_tb where hid='" + str(lid) + "'")
            row_headers = [x[0] for x in cmd.description]
            emd = cmd.fetchall()
            if emd is not None:
                json_data = []
                for result in emd:
                    json_data.append(dict(zip(row_headers, result)))
                    print(json_data)
                return jsonify(json_data)
            else:
                return jsonify({"res": "no"})
        except Exception as e:
            print(str(e))
            return jsonify({'result': "na"})


@app.route('/employee_view_work',methods=['POST','GET'])
def employee_view_work():
    try:

        eid = request.args.get('eid')
        print(eid)
        cmd.execute("SELECT * FROM mh_assign_work WHERE eid='" + str(eid) + "'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
        print(str(e))
        return jsonify({'result': "na"})

@app.route('/employee_send_report', methods=['POST', 'GET'])
def employee_send_report():
            try:

                eid = request.form['eid']
                files = request.files['files']
                wid = request.form['wid']
                fname = secure_filename(files.filename)
                files.save(os.path.join(path1, fname))
                #hid = request.form['hid']
                print("insert into m_emp_send_report values(null,'" + eid + "','" +wid + "',''" + fname + "',curdate())")
                cmd.execute("insert into m_emp_send_report values(null,'" + eid + "','" +wid + "','" + fname + "',curdate())")
                con.commit()
                return jsonify({'task': "success"})
            except Exception as e:
                print(e)
                return jsonify({'task': "failed"})



#### PHONE CONTROLL   ####


@app.route('/PhoneLogin',methods=['GET','POST'])
def PhoneLogin():
    imei=request.args.get('imei')
    cmd.execute("select * from phone_reg where imei='"+imei+"'")
    s = cmd.fetchone()
    print(s)
    if (s is None):
        return jsonify({'result':"no"})
    else:
        return jsonify({'result': str(s[2])})


@app.route('/view_phone',methods=['POST','GET'])
def view_phone():
    try:
        lid = request.args.get('lid')
        cmd.execute("select hid,imei from phone_reg where hid='" + str(lid) + "'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
        print(str(e))
        return jsonify({'result': "na"})

@app.route('/assign_phone', methods=['POST', 'GET'])
def assign_phone():

        imei = request.args.get('imei')
        eid = request.args.get('eid')
        cmd.execute("insert into assign_phone values(null,'" + imei + "','" +eid+ "')")
        con.commit()
        return jsonify({'result': "success"})


@app.route('/InsertCall', methods=['POST', 'GET'])
def InsertCall():
    imei =  request.args.get('imei')
    number=request.args.get('number')
    duration = request.args.get('duration')
    type = request.args.get('type')
    cmd.execute("insert into call_log values(null,'"+imei+"','"+number+"','"+duration+"','"+type+"',curdate(),curtime())")
    con.commit()
    return jsonify({'result': "success"})

@app.route('/call_log',methods=['GET','POST'])
def call_log():
    imei=request.args.get('imei')
    cmd.execute("select * from call_log where imei='"+imei+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    # print(json_data)f
    con.commit()
    return jsonify(json_data)



@app.route('/InsertBackup', methods=['POST', 'GET'])
def InsertBackup():
    imei =  request.args.get('imei')
    res = request.args.get('res')
    f=res.split('@')
    for file in f:
        cmd.execute("select * from back_up_file where imei='"+imei+"' and f_name='"+file+"'")
        s=cmd.fetchone()
        if(s is None):
            cmd.execute("insert into back_up_file values(null,'"+imei+"','"+file+"','pending')")
            con.commit()

    return jsonify({'result': "success"})

@app.route('/InsertErase', methods=['POST', 'GET'])
def InsertErase():
        imei = request.args.get('imei')
        res = request.args.get('res')
        f=res.split('@')
        for file in f:
            cmd.execute("select * from erase_file where imei='" + imei + "' and f_name='" + file + "'")
            s = cmd.fetchone()
            if (s is None):
                cmd.execute("insert into erase_file values(null,'" + imei + "','" + file + "','pending')")
                con.commit()
                return jsonify({'result': "success"})
            else:
                return jsonify({'result': "invalid"})




@app.route('/msg_log_head',methods=['GET','POST'])
def msg_log_head():
    imei=request.args.get('imei')
    cmd.execute("select * from msg_log where imei='"+imei+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    print(json_data)
    con.commit()
    return jsonify(json_data)




@app.route('/Location', methods=['POST', 'GET'])
def Location():
    lati =  request.args.get('lati')
    longi = request.args.get('longi')
    imei = request.args.get('imei')
    cmd.execute("select * from location where imei='"+imei+"'")
    s=cmd.fetchone()
    if(s is None):
        cmd.execute("insert into location values(null,'"+imei+"','"+lati+"','"+longi+"',curdate())")
        con.commit()
        return jsonify({'result': "success"})
    else:
        cmd.execute("update location set lattitude='" + lati + "',longitude='" + longi + "',date=curdate() where imei='" + imei + "'")
        con.commit()
        return jsonify({'result': "success"})

@app.route('/viewlocation', methods=['GET', 'POST'])
def viewlocation():
        imei = request.args.get('imei')
        cmd.execute("SELECT * from location WHERE imei='" + imei + "'")
        row_headers = [x[0] for x in cmd.description]
        results = cmd.fetchone()
        if results is not None:
            lati=results[2]
            longi = results[3]
            date = results[4]
            r=lati+"#"+longi

            return jsonify({'res': r})
        else:
            return jsonify({'res': "no"})


@app.route('/MessageLog', methods=['POST', 'GET'])
def MessageLog():
        imei = request.args.get('imei')
        phno = request.args.get('phno')
        msg = request.args.get('msg')
        type = request.args.get('type')
        cmd.execute("insert into msg_log values(null,'" + imei + "','" + phno + "','" + msg + "','" + type + "',curdate(),curtime())")
        con.commit()
        return jsonify({'result': "success"})


@app.route('/Notification',methods=['GET','POST'])
def Notification():
    lid=request.args.get('id')
    cmd.execute("SELECT `message`.`msg_name`,`message`.`msg_details`,`a_add_tb`.`name` FROM `message` INNER JOIN `a_add_tb` ON `message`.`hid`=`a_add_tb`.`hid` WHERE `message`.`eid`='"+lid+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    # print(json_data)
    return jsonify(json_data)

@app.route('/Files', methods=['POST', 'GET'])
def Files():
    imei =  request.args.get('imei')
    imgg = request.args.get('file')
    x = imgg.split("@")
    for i in x:
        cmd.execute("select * from file_log where imei='"+imei+"' and file='"+i+"'")
        s=cmd.fetchone()
        # print("sssssssss",s)
        if(s is None):
            # print("hiiiiiiiiiiii")
            cmd.execute("insert into file_log values(null,'" + imei + "','" + i + "',curdate())")
            con.commit()

    return jsonify({'result': "ok"})



@app.route('/FileView',methods=['GET','POST'])
def FileView():
    imei=request.args.get('imei')
    cmd.execute("select * from file_log where imei='"+imei+"' and file not in (select f_name from erase_file where imei='"+imei+"')")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@app.route('/ViewFile',methods=['GET','POST'])
def ViewFile():
    imei=request.args.get('imei')
    cmd.execute("select * from  back_up_file where imei='"+imei+"' and status='pending'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    return jsonify(json_data)

@app.route('/UpdateDelete', methods=['POST', 'GET'])
def UpdateDelete():
    fid =  request.args.get('fid')
    cmd.execute("update erase_file set status='deleted' where ers_id='"+fid+"'")
    con.commit()
    return jsonify({'result': "success"})

@app.route('/UpdateSend', methods=['POST', 'GET'])
def UpdateSend():
    fid=request.form['fileid']
    imgg = request.files['files']
    fname = secure_filename(imgg.filename)
    imgg.save(os.path.join(path, fname))
    cmd.execute("update  back_up_file set status='backuped' where f_name like '%"+fname+"'")
    con.commit()
    return jsonify({'task': "success"})

@app.route('/ViewDelete',methods=['GET','POST'])
def ViewDelete():
    imei=request.args.get('imei')
    cmd.execute("select * from erase_file where imei='"+imei+"' and status='pending'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    return jsonify(json_data)

@app.route('/EraseFile',methods=['GET','POST'])
def EraseFile():
    imei=request.args.get('imei')
    cmd.execute("select * from file_log where imei='"+imei+"' and file not in (select f_name from erase_file where imei='"+imei+"')")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    # print(json_data)
    return jsonify(json_data)




if __name__ == "__main__":
    app.run(host="192.168.43.190", port=5000)