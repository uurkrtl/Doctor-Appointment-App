import {Route, Routes} from "react-router-dom";
import AppointmentDraftAdd from "../pages/AppointmentDraftAdd.tsx";
import AppointmentDetailAdd from "../pages/AppointmentDetailAdd.tsx";

function Dashboard() {
    return (
        <div className="mt-5">
            <Routes>
                <Route path={'/'} element={<AppointmentDraftAdd/>}/>
                <Route path={'/appointments/add-details/:id'} element={<AppointmentDetailAdd/>}/>
            </Routes>
        </div>
    );
}

export default Dashboard;