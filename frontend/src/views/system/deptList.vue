<template>
  <div class="dept-list-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>部门管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增
          </el-button>
        </div>
      </template>

      <el-table :data="deptTree" v-loading="loading" row-key="id" default-expand-all stripe>
        <el-table-column prop="deptName" label="部门名称" width="200">
          <template #default="{ row }">
            <el-icon style="margin-right: 5px;"><OfficeBuilding /></el-icon>
            <span>{{ row.deptName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="100" />
        <el-table-column prop="leader" label="负责人" width="120" />
        <el-table-column prop="phone" label="联系电话" width="140" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'">
              {{ row.status === '0' ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button type="success" link @click="handleAddChild(row)">
              <el-icon><Plus /></el-icon>
              新增
            </el-button>
            <el-button type="danger" link @click="handleDelete(row)">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
      @close="handleClose"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="上级部门" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="deptOptions"
            :props="{ label: 'deptName', value: 'id', children: 'children' }"
            placeholder="请选择上级部门"
            clearable
            check-strictly
            default-expand-all
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="显示排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.leader" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio value="0">正常</el-radio>
            <el-radio value="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDepts, createDept, updateDept, deleteDept } from '@/api/dept'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('新增部门')
const formRef = ref(null)
const deptList = ref([])

const deptTree = computed(() => {
  return buildTree(deptList.value)
})

const deptOptions = computed(() => {
  const options = [{ id: 0, deptName: '根部门', children: [] }]
  const treeData = buildTree(deptList.value)
  options[0].children = treeData
  return options
})

const form = reactive({
  id: null,
  parentId: 0,
  deptName: '',
  sort: 0,
  leader: '',
  phone: '',
  email: '',
  status: '0'
})

const rules = {
  deptName: [
    { required: true, message: '请输入部门名称', trigger: 'blur' }
  ],
  parentId: [
    { required: true, message: '请选择上级部门', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

const buildTree = (data) => {
  const map = {}
  const roots = []
  
  data.forEach(item => {
    map[item.id] = { ...item, children: [] }
  })
  
  data.forEach(item => {
    const node = map[item.id]
    if (item.parentId === 0) {
      roots.push(node)
    } else if (map[item.parentId]) {
      map[item.parentId].children.push(node)
    }
  })
  
  return roots
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getDepts()
    deptList.value = res.data
  } catch (error) {
    console.error('获取数据失败:', error)
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增部门'
  resetForm()
  dialogVisible.value = true
}

const handleAddChild = (row) => {
  dialogTitle.value = '新增部门'
  resetForm()
  form.parentId = row.id
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑部门'
  resetForm()
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  if (row.children && row.children.length > 0) {
    ElMessage.warning('存在子部门,不允许删除')
    return
  }
  
  ElMessageBox.confirm('确定要删除该部门吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteDept(row.id)
      ElMessage.success('删除成功')
      fetchData()
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        if (form.id) {
          await updateDept(form)
          ElMessage.success('修改成功')
        } else {
          await createDept(form)
          ElMessage.success('新增成功')
        }
        dialogVisible.value = false
        fetchData()
      } catch (error) {
        console.error('提交失败:', error)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

const resetForm = () => {
  form.id = null
  form.parentId = 0
  form.deptName = ''
  form.sort = 0
  form.leader = ''
  form.phone = ''
  form.email = ''
  form.status = '0'
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

const handleClose = () => {
  resetForm()
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.dept-list-container {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
