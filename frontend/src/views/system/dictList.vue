<template>
  <div class="dict-list-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">
              <span>字典类型</span>
              <el-button type="primary" size="small" @click="handleAddType">
                <el-icon><Plus /></el-icon>
                新增
              </el-button>
            </div>
          </template>
          
          <el-form :inline="true" :model="searchForm" class="search-form">
            <el-form-item>
              <el-input
                v-model="searchForm.dictName"
                placeholder="字典名称"
                clearable
                @clear="searchTypes"
                @keyup.enter="searchTypes"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="small" @click="searchTypes">
                <el-icon><Search /></el-icon>
              </el-button>
            </el-form-item>
          </el-form>

          <el-table
            :data="typeList"
            v-loading="typeLoading"
            stripe
            highlight-current-row
            @current-change="handleTypeChange"
          >
            <el-table-column prop="dictName" label="字典名称" />
            <el-table-column prop="dictType" label="字典类型" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }">
                <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">
                  {{ row.status === '0' ? '正常' : '停用' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
            v-model:current-page="typePagination.current"
            v-model:page-size="typePagination.size"
            :page-sizes="[10, 20, 50]"
            :total="typePagination.total"
            layout="total, prev, pager, next"
            @size-change="fetchTypes"
            @current-change="fetchTypes"
            class="pagination"
            small
          />
        </el-card>
      </el-col>
      
      <el-col :span="16">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">
              <span>字典数据</span>
              <el-button
                type="primary"
                size="small"
                @click="handleAddData"
                :disabled="!currentType"
              >
                <el-icon><Plus /></el-icon>
                新增
              </el-button>
            </div>
          </template>

          <el-form :inline="true" :model="dataSearchForm" class="search-form">
            <el-form-item label="字典标签">
              <el-input
                v-model="dataSearchForm.dictLabel"
                placeholder="请输入字典标签"
                clearable
              />
            </el-form-item>
            <el-form-item label="状态">
              <el-select
                v-model="dataSearchForm.status"
                placeholder="请选择状态"
                clearable
              >
                <el-option label="正常" value="0" />
                <el-option label="停用" value="1" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearchData">
                <el-icon><Search /></el-icon>
                搜索
              </el-button>
              <el-button @click="handleResetData">
                <el-icon><Refresh /></el-icon>
                重置
              </el-button>
            </el-form-item>
          </el-form>

          <el-table :data="dataList" v-loading="dataLoading" stripe>
            <el-table-column type="selection" width="50" />
            <el-table-column prop="dictSort" label="字典排序" width="100" />
            <el-table-column prop="dictLabel" label="字典标签" width="150" />
            <el-table-column prop="dictValue" label="字典键值" width="150" />
            <el-table-column prop="cssClass" label="样式属性" width="120" />
            <el-table-column prop="listClass" label="表格回显样式" width="120">
              <template #default="{ row }">
                <el-tag :type="getTagType(row.listClass)">
                  {{ row.dictLabel }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }">
                <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">
                  {{ row.status === '0' ? '正常' : '停用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150" fixed="right">
              <template #default="{ row }">
                <el-button type="primary" link @click="handleEditData(row)">
                  <el-icon><Edit /></el-icon>
                </el-button>
                <el-button type="danger" link @click="handleDeleteData(row)">
                  <el-icon><Delete /></el-icon>
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
            v-model:current-page="dataPagination.current"
            v-model:page-size="dataPagination.size"
            :page-sizes="[10, 20, 50]"
            :total="dataPagination.total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="fetchDatas"
            @current-change="fetchDatas"
            class="pagination"
          />
        </el-card>
      </el-col>
    </el-row>

    <el-dialog
      v-model="typeDialogVisible"
      :title="typeDialogTitle"
      width="500px"
      @close="handleCloseType"
    >
      <el-form
        ref="typeFormRef"
        :model="typeForm"
        :rules="typeRules"
        label-width="80px"
      >
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="typeForm.dictName" placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input
            v-model="typeForm.dictType"
            placeholder="请输入字典类型"
            :disabled="!!typeForm.id"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="typeForm.status">
            <el-radio value="0">正常</el-radio>
            <el-radio value="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="typeForm.remark"
            type="textarea"
            placeholder="请输入备注"
            :rows="2"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="typeDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitType" :loading="submitLoading">
          确定
        </el-button>
      </template>
    </el-dialog>

    <el-dialog
      v-model="dataDialogVisible"
      :title="dataDialogTitle"
      width="600px"
      @close="handleCloseData"
    >
      <el-form
        ref="dataFormRef"
        :model="dataForm"
        :rules="dataRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="字典排序" prop="dictSort">
              <el-input-number v-model="dataForm.dictSort" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="字典标签" prop="dictLabel">
              <el-input v-model="dataForm.dictLabel" placeholder="请输入字典标签" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="字典键值" prop="dictValue">
              <el-input v-model="dataForm.dictValue" placeholder="请输入字典键值" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="dataForm.status">
                <el-radio value="0">正常</el-radio>
                <el-radio value="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="样式属性">
              <el-input v-model="dataForm.cssClass" placeholder="请输入样式属性" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="表格回显样式">
              <el-select v-model="dataForm.listClass" placeholder="请选择样式" clearable>
                <el-option label="默认" value="default" />
                <el-option label="主要" value="primary" />
                <el-option label="成功" value="success" />
                <el-option label="警告" value="warning" />
                <el-option label="危险" value="danger" />
                <el-option label="信息" value="info" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input
            v-model="dataForm.remark"
            type="textarea"
            placeholder="请输入备注"
            :rows="2"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dataDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitData" :loading="submitLoading">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getDictTypes, createDictType, updateDictType, deleteDictType,
  getDictDatas, createDictData, updateDictData, deleteDictData
} from '@/api/dict'

const typeLoading = ref(false)
const dataLoading = ref(false)
const submitLoading = ref(false)
const typeDialogVisible = ref(false)
const dataDialogVisible = ref(false)
const typeDialogTitle = ref('新增字典类型')
const dataDialogTitle = ref('新增字典数据')
const typeFormRef = ref(null)
const dataFormRef = ref(null)
const typeList = ref([])
const dataList = ref([])
const currentType = ref(null)

const searchForm = reactive({
  dictName: ''
})

const dataSearchForm = reactive({
  dictLabel: '',
  status: ''
})

const typePagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const dataPagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const typeForm = reactive({
  id: null,
  dictName: '',
  dictType: '',
  status: '0',
  remark: ''
})

const dataForm = reactive({
  id: null,
  dictType: '',
  dictSort: 0,
  dictLabel: '',
  dictValue: '',
  cssClass: '',
  listClass: '',
  status: '0',
  remark: ''
})

const typeRules = {
  dictName: [
    { required: true, message: '请输入字典名称', trigger: 'blur' }
  ],
  dictType: [
    { required: true, message: '请输入字典类型', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

const dataRules = {
  dictSort: [
    { required: true, message: '请输入字典排序', trigger: 'blur' }
  ],
  dictLabel: [
    { required: true, message: '请输入字典标签', trigger: 'blur' }
  ],
  dictValue: [
    { required: true, message: '请输入字典键值', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

const getTagType = (type) => {
  const typeMap = {
    'primary': 'primary',
    'success': 'success',
    'warning': 'warning',
    'danger': 'danger',
    'info': 'info'
  }
  return typeMap[type] || ''
}

const fetchTypes = async () => {
  typeLoading.value = true
  try {
    const params = {
      current: typePagination.current,
      size: typePagination.size,
      ...searchForm
    }
    const res = await getDictTypes(params)
    typeList.value = res.data.records
    typePagination.total = res.data.total
  } catch (error) {
    console.error('获取字典类型失败:', error)
  } finally {
    typeLoading.value = false
  }
}

const searchTypes = () => {
  typePagination.current = 1
  fetchTypes()
}

const handleTypeChange = (row) => {
  currentType.value = row
  dataForm.dictType = row.dictType
  dataPagination.current = 1
  fetchDatas()
}

const fetchDatas = async () => {
  if (!currentType.value) return
  
  dataLoading.value = true
  try {
    const params = {
      current: dataPagination.current,
      size: dataPagination.size,
      dictType: currentType.value.dictType,
      ...dataSearchForm
    }
    const res = await getDictDatas(params)
    dataList.value = res.data.records
    dataPagination.total = res.data.total
  } catch (error) {
    console.error('获取字典数据失败:', error)
  } finally {
    dataLoading.value = false
  }
}

const handleSearchData = () => {
  dataPagination.current = 1
  fetchDatas()
}

const handleResetData = () => {
  dataSearchForm.dictLabel = ''
  dataSearchForm.status = ''
  handleSearchData()
}

const handleAddType = () => {
  typeDialogTitle.value = '新增字典类型'
  resetTypeForm()
  typeDialogVisible.value = true
}

const handleEditType = (row) => {
  typeDialogTitle.value = '编辑字典类型'
  resetTypeForm()
  Object.assign(typeForm, row)
  typeDialogVisible.value = true
}

const handleDeleteType = (row) => {
  ElMessageBox.confirm('确定要删除该字典类型吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteDictType(row.id)
      ElMessage.success('删除成功')
      fetchTypes()
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

const handleSubmitType = async () => {
  if (!typeFormRef.value) return
  
  await typeFormRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        if (typeForm.id) {
          await updateDictType(typeForm)
          ElMessage.success('修改成功')
        } else {
          await createDictType(typeForm)
          ElMessage.success('新增成功')
        }
        typeDialogVisible.value = false
        fetchTypes()
      } catch (error) {
        console.error('提交失败:', error)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

const resetTypeForm = () => {
  typeForm.id = null
  typeForm.dictName = ''
  typeForm.dictType = ''
  typeForm.status = '0'
  typeForm.remark = ''
  if (typeFormRef.value) {
    typeFormRef.value.resetFields()
  }
}

const handleCloseType = () => {
  resetTypeForm()
}

const handleAddData = () => {
  if (!currentType.value) {
    ElMessage.warning('请先选择字典类型')
    return
  }
  dataDialogTitle.value = '新增字典数据'
  resetDataForm()
  dataForm.dictType = currentType.value.dictType
  dataDialogVisible.value = true
}

const handleEditData = (row) => {
  dataDialogTitle.value = '编辑字典数据'
  resetDataForm()
  Object.assign(dataForm, row)
  dataDialogVisible.value = true
}

const handleDeleteData = (row) => {
  ElMessageBox.confirm('确定要删除该字典数据吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteDictData(row.id)
      ElMessage.success('删除成功')
      fetchDatas()
    } catch (error) {
      console.error('删除失败:', error)
    }
  }).catch(() => {})
}

const handleSubmitData = async () => {
  if (!dataFormRef.value) return
  
  await dataFormRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        if (dataForm.id) {
          await updateDictData(dataForm)
          ElMessage.success('修改成功')
        } else {
          await createDictData(dataForm)
          ElMessage.success('新增成功')
        }
        dataDialogVisible.value = false
        fetchDatas()
      } catch (error) {
        console.error('提交失败:', error)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

const resetDataForm = () => {
  dataForm.id = null
  dataForm.dictType = ''
  dataForm.dictSort = 0
  dataForm.dictLabel = ''
  dataForm.dictValue = ''
  dataForm.cssClass = ''
  dataForm.listClass = ''
  dataForm.status = '0'
  dataForm.remark = ''
  if (dataFormRef.value) {
    dataFormRef.value.resetFields()
  }
}

const handleCloseData = () => {
  resetDataForm()
}

onMounted(() => {
  fetchTypes()
})
</script>

<style scoped>
.dict-list-container {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 15px;
}

.pagination {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
}
</style>
