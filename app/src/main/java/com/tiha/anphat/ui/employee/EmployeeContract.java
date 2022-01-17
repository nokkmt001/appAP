package com.tiha.anphat.ui.employee;

import com.tiha.anphat.data.entities.EmployeeInfo;

import java.util.List;

interface EmployeeContract {
    interface View{
        void onGetListEmployeeSuccess(List<EmployeeInfo> list);

        void onGetListEmployeeError(String error);
    }

    interface Presenter{
        void GetListEmployee(String MCN);

    }

}
