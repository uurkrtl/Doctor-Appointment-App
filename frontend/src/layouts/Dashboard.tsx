import {Route, Routes} from "react-router-dom";
import AppointmentDraftAdd from "../pages/AppointmentDraftAdd.tsx";

function Dashboard() {
    return (
        <div className="mt-5">
            <Routes>
                <Route path={'/'} element={<AppointmentDraftAdd/>}/>
            </Routes>
        </div>
    );
}

export default Dashboard;