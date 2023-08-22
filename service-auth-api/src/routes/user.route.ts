import express from "express";
import { getAllUsersHandler, getMeHandler, getUserById } from "../controllers/user.controller";
import { deserializeUser } from "../middleware/deserializeUser";
import { requireUser } from "../middleware/requireUser";
import { restrictTo } from "../middleware/restrictTo";

const router = express.Router();

router.get('/user/:id', getUserById);

router.use(deserializeUser, requireUser);

router.get('/', restrictTo('admin'), getAllUsersHandler);
router.get('/me', getMeHandler);

export default router;